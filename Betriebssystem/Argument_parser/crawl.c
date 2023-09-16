// TODO: includes, global vars, etc
#include <stdio.h>
#include <stdlib.h>
#include <regex.h>
#include "argumentParser.h"
#include <string.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <libgen.h>
#include <fnmatch.h>
#include <fcntl.h>
#include <dirent.h>
#include <stdbool.h>

// TODO: implement helper functions: makes constraint checking easier

bool start = true;

void checkStat(char* actualPath, const char pattern[], char* fName, mode_t fType, off_t fSize, char type, int sizeMode, off_t size, regex_t* line_regex){
	bool found = true;
        bool printLine = false;
        if(pattern != NULL){
        	int matchName = fnmatch(pattern,fName,FNM_PERIOD);
                if(matchName != 0)found = false;
        }

        if( (type == 'd' && !S_ISDIR(fType)) || (type == 'f' && !S_ISREG(fType)) ){
        	found = false;
        }

		// KORREKTUR: So kann nicht mit size = 0 verglichen werden. (-0.5P)
        if(size > 0){
        	if( (sizeMode == 2 && fSize != size) || (sizeMode == 1 && fSize < size) || (sizeMode == 0 && fSize > size) )found = false;
        }

        if(line_regex != NULL){
        	printLine = true;
                if(!S_ISDIR(fType) && found){
                	int line = 1;
                        char* content;
                        size_t len = 0;
                        int counter = 0;
						// KORREKTUR: Die Fehlerbehandlung für fopen fehlt. (-0.5P)
                        FILE* fp = fopen(actualPath,"r");
                        while(getline(&content,&len,fp) != -1){
                        	int error = regexec(line_regex,content,0,NULL,0);
                                if(!error){
                                	printf("%s:%d:%s",actualPath,line,content);
                                        counter++;
                                }
                                line++;
                       	}
                        if(counter == 0)found = false;
                        if(content)free(content);
                        fclose(fp);
                }
       	}
	if(found && !printLine){
        	printf("%s\n",actualPath);
        }
}

static void crawl(char path[], int maxDepth, const char pattern[], char type,
                  int sizeMode, off_t size, regex_t * line_regex) {
	// TODO: implement body
	// KORREKTUR: Ihr dürft nicht davon ausgehen, dass es sich hier zwingend um ein Verzeichnis handelt. Es können auch Dateien als Argumente übergeben werden. (-1P)
	DIR* d = opendir(path);
	struct dirent* f = NULL;
	if(d == NULL || maxDepth <= 0){
		closedir(d);
		return;
	}

	//get stat of the very first directory
	if(start){
		int startIndex = 0;
                for(int i = 0; i < strlen(path); i++){
                        if(path[i] == '/')startIndex = i;
                }
                int nameLen = strlen(path) - startIndex - 1;
				// KORREKTUR: Fehlerbehandlung für malloc fehlt (-0.5P)
                char* fName = malloc(nameLen+1);
                memcpy(fName,&path[startIndex+1],nameLen);
		memcpy(fName + nameLen,"\0",1);
		struct stat st;
		int lst = lstat(path,&st);
		// ANMERKUNG: Hier wäre dann eine Fehlermeldung gut, wenn lstat fehlschlägt.
		if(lst == 0){
			mode_t fType = st.st_mode;
			off_t fSize = st.st_size;
			checkStat(path,pattern,fName,fType,fSize,type,sizeMode,size,line_regex);
		}
		free(fName);
		start = false;
	}

	while((f = readdir(d)) != NULL){
		char* fName = f->d_name;
		int pathLength = strlen(path);
		int mem = pathLength + 1 + strlen(fName) + 1;
		char* actualPath = malloc(mem);

		memcpy(actualPath, path, pathLength);
		memcpy(actualPath + pathLength, "/", 1);
		memcpy(actualPath + pathLength + 1, fName, strlen(fName) + 1);

		struct stat st;
		int lst = lstat(actualPath,&st);
		if(lst == 0){
			mode_t fType = st.st_mode;
			off_t fSize = st.st_size;

			if(strcmp(fName,".") == 0 || strcmp(fName,"..") == 0 || (!S_ISREG(fType) && !S_ISDIR(fType)) ){
				free(actualPath);
				continue;
			}

			checkStat(actualPath,pattern,fName,fType,fSize,type,sizeMode,size,line_regex);

			if(S_ISDIR(fType)){
				crawl(actualPath,maxDepth-1,pattern,type,sizeMode,size,line_regex);
			}
			if(actualPath != NULL)free(actualPath);
		}else{
			perror("lstat error");
		}
	}
	start = true;
	closedir(d);
}




int main(int argc, char *argv[]) {
	// TODO: use argumentParser to retrieve arguments
	// TODO: invoke crawl() with args on all given pathes
	/* KORREKTUR: Ihr prüft nicht, ob die übergebenen Argumente dem erwarteten Format entsprechen (-1P) 
		Mit strtol() gemäß dem Beispiel in der Manpage wäre es möglich, auch bei der Konvertierung der Zahlen Fehlerbehandlung einzubauen.
	*/
	// KORREKTUR: Das ist keine unbeschränkte Suchtiefe. (-0.5P)
	int maxDEPTH = 1024;
	int a = initArgumentParser(argc,argv);
	if(a == 0){
		int argNum = getNumberOfArguments();
		if(argNum == 0){
			// ANMERKUNG: Hier wäre dann eine "richtige" Usage-Ausgabe gut.
			printf("Must have at least 1 path\n");
			return 0;
		}

		if(getValueForOption("maxdepth") != NULL)maxDEPTH = abs(atoi(getValueForOption("maxdepth")));

		char* name = getValueForOption("name");
		char* typeStr = getValueForOption("type");
		char type = 'u';

		if(typeStr != NULL)type = typeStr[0];

		char* sizeStr = getValueForOption("size");
		int size = 0;
		int mode = 0;
		if(sizeStr != NULL){
			if(sizeStr[0] == '-')mode = 0;
			else if(sizeStr[0] == '+')mode = 1;
			else mode = 2;
			size = abs(atoi(sizeStr));
		}

		if(getValueForOption("line") != NULL){
			regex_t line;
			int err = regcomp(&line,getValueForOption("line"),REG_EXTENDED);
			for(int i = 0; i < getNumberOfArguments(); i++){
				// KORREKTUR: start wird nicht zurückgesetzt. (-0.5P)
				if(err){
					fprintf(stderr,"Could not compile regex\n");
					crawl(getArgument(i),maxDEPTH,name,type,mode,size,NULL);
				}else{
					crawl(getArgument(i),maxDEPTH,name,type,mode,size,&line);
				}
				// KORREKTUR: Ihr ruft hier regfree auf, obwohl die regex noch im nächsten Durchlauf der Schleife gebraucht wird. (-0.5P)
				regfree(&line);
			}
		}else{
			for(int i = 0; i < getNumberOfArguments(); i++){
				crawl(getArgument(i),maxDEPTH,name,type,mode,size,NULL);
			}
		}
	}
	//printf("test4\n");
	exit(EXIT_SUCCESS);
	return a;
}
// DUrchlauf:
// PUNKTE: 0
// Suchtiefe:
// PUNKTE: 0.5
// Ausgabe
// PUNKTE: 2.5
