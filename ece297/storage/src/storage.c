/**
 * @file
 * @brief This file contains the implementation of the storage server
 * interface as specified in storage.h.
 */

#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>
#include "storage.h"
#include "utils.h"

/**
 * @brief This is just a minimal stub implementation.  You should modify it 
 * according to your design.
 */
void* storage_connect(const char *hostname, const int port)
{
	
	// it would be useful here to see who we are trying to connect to.
	logger(_SOURCE_STORAGE, "Attempting to connect to %s:%d", hostname, port);

	// Create a socket.
	int sock = socket(PF_INET, SOCK_STREAM, 0);
	if (sock < 0)
		return NULL;

	// Get info about the server.
	struct addrinfo serveraddr, *res;
	memset(&serveraddr, 0, sizeof serveraddr);
	serveraddr.ai_family = AF_UNSPEC;
	serveraddr.ai_socktype = SOCK_STREAM;
	char portstr[MAX_PORT_LEN];
	snprintf(portstr, sizeof portstr, "%d", port);
	int status = getaddrinfo(hostname, portstr, &serveraddr, &res);
	if (status != 0){

		// in case if the server is unavailable we should know.
		logger(_SOURCE_STORAGE, "Server at %s:%d unavailable.", hostname, port);

		return NULL;
	}

	// Connect to the server.
	status = connect(sock, res->ai_addr, res->ai_addrlen);
	if (status != 0){

		// in case of a failed socket connection
		logger(_SOURCE_STORAGE, "Cannot connect to socket.");

		return NULL;
	}

	// or, if the connection was established successfully. awesome.
	logger(_SOURCE_STORAGE, "Connection established successfully."); // replace LOG commands with logger() calls


	return (void*) sock;
}


/**
 * @brief This is just a minimal stub implementation.  You should modify it 
 * according to your design.
 */
int storage_auth(const char *username, const char *passwd, void *conn)
{
	// see who we are trying to authenticate.
	logger(_SOURCE_STORAGE, "Authenticating user: ", username); // replace LOG commands with logger() calls

	// Connection is really just a socket file descriptor.
	int sock = (int)conn;

	// Send some data.
	char buf[MAX_CMD_LEN];
	memset(buf, 0, sizeof buf);
	char *encrypted_passwd = generate_encrypted_password(passwd, NULL);
	snprintf(buf, sizeof buf, "AUTH %s %s\n", username, encrypted_passwd);
	if (sendall(sock, buf, strlen(buf)) == 0 && recvline(sock, buf, sizeof buf) == 0) {
		return 0;

		// if it failed, output a message.
		logger(_SOURCE_STORAGE, "Authentication success."); // replace LOG commands with logger() calls

	}

	// if success, output message as well.
	logger(_SOURCE_STORAGE, "Authentication failed."); // replace LOG commands with logger() calls


	return -1;
}

/**
 * @brief This is just a minimal stub implementation.  You should modify it 
 * according to your design.
 */
int storage_get(const char *table, const char *key, struct storage_record *record, void *conn)
{
	// see what we are trying to get access to.
	logger(_SOURCE_STORAGE, "SELECT FROM TABLE %s WHERE (KEY == %s)", table, key); 

	// replace LOG commands with logger() calls

	// Connection is really just a socket file descriptor.
	int sock = (int)conn;

	// Send some data.
	char buf[MAX_CMD_LEN];
	memset(buf, 0, sizeof buf);
	snprintf(buf, sizeof buf, "GET %s %s\n", table, key);
	if (sendall(sock, buf, strlen(buf)) == 0 && recvline(sock, buf, sizeof buf) == 0) {
		strncpy(record->value, buf, sizeof record->value);

		return 0;
	}

	// if the record doesnt exist.  we should tell them this error.
	logger(_SOURCE_STORAGE, "No such record."); 

	return -1;
}


/**
 * @brief This is just a minimal stub implementation.  You should modify it 
 * according to your design.
 */
int storage_set(const char *table, const char *key, struct storage_record *record, void *conn)
{

	// see what we are trying to update.
	logger(_SOURCE_STORAGE, "UPDATE TABLE %s SET %s", table, key);

	// Connection is really just a socket file descriptor.
	int sock = (int)conn;

	// Send some data.
	char buf[MAX_CMD_LEN];
	memset(buf, 0, sizeof buf);
	snprintf(buf, sizeof buf, "SET %s %s %s\n", table, key, record->value);
	if (sendall(sock, buf, strlen(buf)) == 0 && recvline(sock, buf, sizeof buf) == 0) {

		// tell them if update success and a change as made
		logger(_SOURCE_STORAGE, "Update success.", table, key);

		return 0;
	}

	// if the update failed tell them so.
	logger(_SOURCE_STORAGE, "Update failed.", table, key);

	return -1;
}


/**
 * @brief This is just a minimal stub implementation.  You should modify it 
 * according to your design.
 */
int storage_disconnect(void *conn)
{

	// output a message when the connection is being terminated.
	logger(_SOURCE_STORAGE, "Disconnecting...");

	// Cleanup
	int sock = (int)conn;
	close(sock);

	return 0;
}

