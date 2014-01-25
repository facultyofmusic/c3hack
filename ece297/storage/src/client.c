/**
 * @file
 * @brief This file implements a "very" simple sample client.
 * 
 * The client connects to the server, running at SERVERHOST:SERVERPORT
 * and performs a number of storage_* operations. If there are errors,
 * the client exists.
 */

#include <errno.h>
#include <stdio.h>
#include <string.h>
#include "storage.h"
#include "utils.h"

#define SERVERHOST "localhost"
#define SERVERPORT 1111
#define SERVERUSERNAME "admin"
#define SERVERPASSWORD "dog4sale"
#define TABLE "marks"
#define KEY "ece297"

#define LOGGING 2


/**
 * @brief Start a client to interact with the storage server.
 *
 * If connect is successful, the client performs a storage_set/get() on
 * TABLE and KEY and outputs the results on stdout. Finally, it exists
 * after disconnecting from the server.
 */
 int main(int argc, char *argv[]) {

  clientInitLogger(LOGGING);

  streamInputs();

  // Exit
  clientTerminateLogger();
  return 0;
}


int streamInputs(){

  int selectionNum;

  struct storage_record r;
  int status;
  void *conn;
  
  
  while(1){


    printf("--------------------------------\n");
    printf(" 1) Connect\n");
    printf(" 2) Authenticate\n");
    printf(" 3) Get\n");
    printf(" 4) Set\n");
    printf(" 5) Disconnect\n");
    printf(" 6) Exit\n");
    printf("--------------------------------\n");



    printf("Please enter your selection: \n");
    scanf("%d",&selectionNum);


    if(selectionNum==1){
      char hostname[256];
      int portNum;

      printf("Please input the hostname: ");
      scanf("%s",hostname);
      printf("Please input the port: ");
      scanf("%d",&portNum);

    // Connect to server
      conn = storage_connect(hostname, portNum);
      if(!conn) {
        printf("Cannot connect to server @ %s:%d. Error code: %d.\n",
         SERVERHOST, SERVERPORT, errno);
        return -1;
      }

      else{
        printf("Connecting to localhost:1111 ...\n");
      }
    }

    if(selectionNum==2){
      char username[256];
      char passwd[256];

      printf("Please input the username: ");
      scanf("%s",username);
      printf("Please input the passwd: ");
      scanf("%s",passwd);

      // Authenticate the client.
      status = storage_auth(username, passwd, conn);
      if(status != 0) {
        printf("storage_auth failed with username '%s' and password '%s'. " \
         "Error code: %d.\n", username, passwd, errno);
        storage_disconnect(conn);
        return status;
      }
      printf("storage_auth: successful.\n");
    }


    if(selectionNum==3){
      char key[256];
      char table[256];
      printf("Please input the key: ");
      scanf("%s",key);
      printf("Please input the table: ");
      scanf("%s",table);

      // Issue storage_get
      status = storage_get(table, key, &r, conn);
      if(status != 0) {
        printf("storage_get failed. Error code: %d.\n", errno);
        storage_disconnect(conn);
        return status;
      }
      printf("storage_get: the value returned for key '%s' is '%s'.\n",
       KEY, r.value);
    }


    if(selectionNum==4){
      int value;
      char key[256];
      char table[256];
      printf("Please input the value: ");
      scanf("%d",&value);
      printf("Please input the key: ");
      scanf("%s",key);
      printf("Please input the table: ");
      scanf("%s",table);
     // Issue storage_set
      strncpy(r.value, "some_value", sizeof r.value);
      status = storage_set(table, key, &r, conn);
      if(status != 0) {
        printf("storage_set failed. Error code: %d.\n", errno);
        storage_disconnect(conn);
        return status;
      }
      printf("storage_set: successful.\n");
    }


    if (selectionNum==5)
    {
      // Disconnect from server
      status = storage_disconnect(conn);
      if(status != 0) {
        printf("storage_disconnect failed. Error code: %d.\n", errno);
        return status;
      }
    }


    if(selectionNum==6){
      break;
    }

  }

  return 0;
}