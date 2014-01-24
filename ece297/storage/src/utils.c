/**
 * @file
 * @brief This file implements various utility functions that are
 * can be used by the storage server and client library. 
 */

#define _XOPEN_SOURCE

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <time.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <unistd.h>
#include <stdarg.h>
#include "utils.h"
//

 int sendall(const int sock, const char *buf, const size_t len)
 {
 	size_t tosend = len;
 	while (tosend > 0) {
 		ssize_t bytes = send(sock, buf, tosend, 0);
 		if (bytes <= 0) 
			break; // send() was not successful, so stop.
		tosend -= (size_t) bytes;
		buf += bytes;
	};

	return tosend == 0 ? 0 : -1;
}

/**
 * In order to avoid reading more than a line from the stream,
 * this function only reads one byte at a time.  This is very
 * inefficient, and you are free to optimize it or implement your
 * own function.
 */
 int recvline(const int sock, char *buf, const size_t buflen)
 {
	int status = 0; // Return status.
	size_t bufleft = buflen;

	while (bufleft > 1) {
		// Read one byte from scoket.
		ssize_t bytes = recv(sock, buf, 1, 0);
		if (bytes <= 0) {
			// recv() was not successful, so stop.
			status = -1;
			break;
		} else if (*buf == '\n') {
			// Found end of line, so stop.
			*buf = 0; // Replace end of line with a null terminator.
			status = 0;
			break;
		} else {
			// Keep going.
			bufleft -= 1;
			buf += 1;
		}
	}
	*buf = 0; // add null terminator in case it's not already there.

	return status;
}


/**
 * @brief Parse and process a line in the config file.
 */
 int process_config_line(char *line, struct config_params *params)
 {
	// Ignore comments.
 	if (line[0] == CONFIG_COMMENT_CHAR)
 		return 0;

	// Extract config parameter name and value.
 	char name[MAX_CONFIG_LINE_LEN];
 	char value[MAX_CONFIG_LINE_LEN];
 	int items = sscanf(line, "%s %s\n", name, value);

	// Line wasn't as expected.
 	if (items != 2)
 		return -1;

	// Process this line.
 	if (strcmp(name, "server_host") == 0) {
 		strncpy(params->server_host, value, sizeof params->server_host);
 	} else if (strcmp(name, "server_port") == 0) {
 		params->server_port = atoi(value);
 	} else if (strcmp(name, "username") == 0) {
 		strncpy(params->username, value, sizeof params->username);
 	} else if (strcmp(name, "password") == 0) {
 		strncpy(params->password, value, sizeof params->password);
 	}
	// else if (strcmp(name, "data_directory") == 0) {
	//	strncpy(params->data_directory, value, sizeof params->data_directory);
	//} 
 	else {
		// Ignore unknown config parameters.
 	}

 	return 0;
 }


 int read_config(const char *config_file, struct config_params *params)
 {
 	int error_occurred = 0;

	// Open file for reading.
 	FILE *file = fopen(config_file, "r");
 	if (file == NULL)
 		error_occurred = 1;

	// Process the config file.
 	while (!error_occurred && !feof(file)) {
		// Read a line from the file.
 		char line[MAX_CONFIG_LINE_LEN];
 		char *l = fgets(line, sizeof line, file);

		// Process the line.
 		if (l == line)
 			process_config_line(line, params);
 		else if (!feof(file))
 			error_occurred = 1;
 	}

 	return error_occurred ? -1 : 0;
 }

 char *generate_encrypted_password(const char *passwd, const char *salt)
 {
 	if(salt != NULL)
 		return crypt(passwd, salt);
 	else
 		return crypt(passwd, DEFAULT_CRYPT_SALT);
 }




/**************************************************
********/


 int clientLoggingMode, serverLoggingMode;

 FILE *clientLogFile, *serverLogFile;


void logger(int source_id, const char* format, ... )
 {

	va_list args;
	va_start (args, format);
	
 	if(source_id == _SOURCE_CLIENT || source_id == _SOURCE_STORAGE){
 		if(clientLoggingMode == C_LOG){
 			if(source_id == _SOURCE_CLIENT){
				printf("[CLIENT] ");
			}else{
				printf("[STORAGE] ");
			}

 			vprintf(format, args);
 			
 			
 		}else if (clientLoggingMode == F_LOG){
 			vfprintf(clientLogFile, format, args);
 			fflush(clientLogFile);

 		}

 	}else if (source_id == _SOURCE_SERVER){
 		if(serverLoggingMode == C_LOG){
			printf("[SERVER] ");
 			vprintf(format, args);
 			
 		}else if (serverLoggingMode == F_LOG){
 			vfprintf(serverLogFile, format, args);
 			fflush(serverLogFile);

 		}

 	}else{
 		printf("[UNKNOWN] ");
 		vprintf(format, args);
 	}
 	
 	va_end (args);
	// fprintf(file,"%s",message);
	// fflush(file);
 }





 int clientInitLogger(int logging_mode){

 	clientLoggingMode = logging_mode;
 	clientLogFile = NULL;

 	if(logging_mode == N_LOG){
 		printf("[LOGGER] Client Logging Disabled\n");
 	}else if (logging_mode == C_LOG){
 		printf("[LOGGER] Client logging to console\n");
 	}else{
 		printf("[LOGGER] Initializing client log file...\n");

 		time_t rawtime;
 		struct tm *info;
 		char buffer[80];

 		time( &rawtime );

 		info = localtime( &rawtime );

 		strftime(buffer,80,"%F-%H-%M-%S", info);

 		char logName[80];

 		memset(logName, 0, 80);

 		strcat(logName,"Client-");
 		strcat(logName,buffer);
 		char *str3=".log";
 		strcat(logName,str3);

 		printf("[LOGGER] Client logging to file: %s\n", logName);

 		clientLogFile = NULL;
 		clientLogFile=fopen(logName,"w");
 	}

 	return 1;
 }

 int clientTerminateLogger(){

 	if(clientLogFile == NULL) return 0;

 	printf("[LOGGER] Closing client log file.\n");
 	fclose(clientLogFile);
 	clientLogFile = NULL;

 	return 1;
 }







// SERVER STUFF!!!







 int serverInitLogger(int logging_mode){

 	serverLoggingMode = logging_mode;
 	serverLogFile = NULL;

 	if(logging_mode == N_LOG){
 		printf("[LOGGER] server Logging Disabled\n");
 	}else if (logging_mode == C_LOG){
 		printf("[LOGGER] server logging to console\n");
 	}else{
 		printf("[LOGGER] Initializing server log file...\n");

 		time_t rawtime;
 		struct tm *info;
 		char buffer[80];

 		time( &rawtime );

 		info = localtime( &rawtime );

 		strftime(buffer,80,"%F-%H-%M-%S", info);

 		char logName[80];

 		memset(logName, 0, 80);

 		strcat(logName,"Server-");
 		strcat(logName,buffer);
 		char *str3=".log";
 		strcat(logName,str3);

 		printf("[LOGGER] server logging to file: %s\n", logName);

 		serverLogFile = NULL;
 		serverLogFile=fopen(logName,"w");
 	}

 	return 1;
 }

 int serverTerminateLogger(){

 	if(serverLogFile == NULL) return 0;

 	printf("[LOGGER] Closing server log file.\n");
 	fclose(serverLogFile);
 	serverLogFile = NULL;

 	return 1;
 }