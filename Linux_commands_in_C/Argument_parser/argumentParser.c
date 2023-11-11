#include "argumentParser.h"

#include <stddef.h>
// TODO: includes
#include <errno.h>
#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include <stdbool.h>
// TODO: global vars
int argNum = 0;
char **args;
// TODO: helpers


int validOption(char* opt){
	for(int i = 0; i < strlen(opt); i++){
		if(i == 0 && opt[i] != '-'){
			return 0;
		}else if(/*i < strlen(opt) && */opt[i] == '='){
			return 1;
		}
	}
	return 0;
}

int initArgumentParser(int argc, char* argv[]) {
	bool isOption = false;
	if(argc == 0 || argv == NULL){
		errno = EINVAL;
		// ANMERKUNG: Hier sollte am besten kein printf stattfinden. Der Argumentparser sollte allgemeingültig sein und nicht auf crawl bezogen. Das nutzende Programm kann dann entscheiden, welche Ausgabe erfolgen soll.
		printf("Usage: ./crawl [PATHS] [OPTIONS]\n");
		return -1;
	}
	for(int i = 1; i < argc; i++){
		if(validOption(argv[i]) && !isOption){
			isOption = true;
		}else if(!validOption(argv[i]) && isOption){
			errno = EINVAL;
			printf("Option %s has wrong format\n",argv[i]);
			return -1;
		}
	}
	argNum = argc;
	args = argv;
	return 0;
	// TODO: implement me
}

char* getCommand(void) {
	// TODO: implement me
	return args[0];
}

int getNumberOfArguments(void) {
	// TODO: implement me
	int track = 0;
	for(int i = 1; i < argNum; i++){
		if(!validOption(args[i])){
			track++;
		}
	}
	return track;
}

char* getArgument(int index) {
	// TODO: implement me
	if(index+1 >= argNum || index < 0){
		printf("Index out of bound\n");
		return NULL;
	}else{
		if(!validOption(args[index+1])){
			return args[index+1];
		}
	}
	return NULL;
}

char* getValueForOption(char* keyName) {
	// TODO: implement me
	//printf("%d\n",argNum);
	size_t keyLength = strlen(keyName);
	for(int i = 1; i < argNum; i++){
		if(validOption(args[i])){
			char* option = args[i]+1;
			size_t optLength = strlen(option);
			//bool check = false;
			for(int k = 0; k < optLength; k++){
				if(k >= keyLength){
					if(option[k] == '='){
						char* retStr = option + k + 1;
						return retStr;
					}else break;
				}else{
					if(option[k] != keyName[k])break;
					/*else{
						printf("found %c\n",option[k]);
					}*/
				}
			}
		}
	}
	return NULL;
}
// PUNKTE: 4