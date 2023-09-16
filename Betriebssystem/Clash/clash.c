// TODO: includes

#include "plist.h"
#include <unistd.h>
#include <sys/wait.h>
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <errno.h>
// TODO: helpers (forward decls) for command parsing, job printing, book keeping
void findZombiefiedProcess(list* list){
	if(list == NULL){ perror("Unidentified list\n"); return; }
	// ANMERKUNG: perror ist nur sinvoll, in verbindung mit errno. wenn kein errno vorhanden ist bitte fehlerausgabe mit fprintf auf dem stderr.
	// ANMERKUNG: hier würde die ausgabe lauten: "Unidentified list: succsess"

	struct qel* le = list->head;
	int status = 0;

	while(le != NULL){
		int wC = waitpid(le->pid, &status, WNOHANG);
		if(wC > 0){
			// KORREKTUR: status beinhaltet wesentlich mehr als nur den exitstatus. DIeser Kann mit WEXITSTATUS(status) ausgelesen werden, falls er prozess regulär terminiert ist WIFEXITED(status) (-1P)
			fprintf(stderr, "Exitstatus [%s] = %d\n", le->cmdLine, status);
			int lePID = le->pid;
			char buf[100];
			le = le->next;
			removeElement(list, lePID, buf, sizeof(buf)/sizeof(char));
			// KORREKTUR: wenn du das entsprechende element freigibst (removeElement) ist danach le->next nicht mehr definiert (-0.5P)
		}else le = le->next;
	}
}

int isRunning(pid_t pid, const char* line){
	int wK = kill(pid,0); // ANMERKUNG: wäre nicht notwendig gewesen
	if(wK == 0){ printf("[pid: %d | command: %s] ", pid, line); return 0; }
	return 1;
}

void printPath(char* buf, long pathLen){
	char* ptr = getcwd(buf, (size_t)pathLen);
        if(ptr != NULL){ fprintf(stderr, "%s: ", ptr); return; }
        perror("Unable to get path");
}

int main () {
	// TODO: implement me
	int status;
	long pathLen = pathconf(".", _PC_PATH_MAX);
	// KORREKTUR:PATH_MAX ist der maximale relative pfad. getcwd gibt aber den absoluten pfad zurück. da kein extra platz für \0 reserviert wurde ist hier der maximale pfad sogar nur PATH_MAX -1 ; (getcwd(NULL,0) war explizit erlaubt) (-1P)
	char* buf = (char*)malloc((size_t)pathLen);
	int maxInputLength = sysconf(_SC_LINE_MAX);
	char* input = malloc(maxInputLength + 100);
	char* printStr = malloc(maxInputLength + 100);
	list* cpl = (list*)malloc(sizeof(list));
	// KORREKTUR: Keine Fehlerbehandlung für sämtliche syscalls (-1P)

	printPath(buf, pathLen);

	while(fgets(input,maxInputLength+100,stdin)){
		// KORREKTUR: warum diese zufällige 100 als zusätzliche länge? Wenn jemand mehr als 100 Zeichen über die max länge hinausschreibt geht würde der rest als zusätzliches Komando interpretiert werden (-0.5P)
		if(strlen(input) <= maxInputLength && input[0] != '\n'){
			input[strlen(input) - 1] = 0;
            		memcpy(printStr, input, strlen(input) + 1);

			char* argList[100] = {NULL};
			int argLIndex = 0;
			char* token = strtok(input, " ");
			bool bg = false;

			while(token != NULL){
				argList[argLIndex] = token;
				argLIndex++;
				token = strtok(NULL," ");
				// KORREKTUR: max 100 argumente ist willkürliche Größe. Größenbeschränkung wird nicht kontrolliert!! (-1P)
			}

			if(strcmp(argList[argLIndex-1], "&") == 0){
				argList[argLIndex-1] = NULL;
				printStr[strlen(printStr) - 1] = 0;
				bg = true;
			}

			if(strcmp(argList[0], "cd") == 0){
                        	if(argLIndex > 2 || argLIndex == 1){
                                	fprintf(stderr, "Usage for cd: cd path\n");
                                }else{
                                        int err = chdir(argList[1]);
                                        if(err != 0){
                                        	fprintf(stderr, "chdir() to %s failed. Path not exist.\n", argList[1]);
											// ANMERKUNG: könnten auch viele andere gründe sein. da der grund im errno steht würde perror diesen Grund mit ausgeben.
                                        }
                                }
								// KORREKTUR: nach/vor cd werden keine Zombieprozesse eingesammelt (-0.25P)
                                printPath(buf, pathLen);
                                continue;

                        }else if(strcmp(argList[0], "jobs") == 0){
							findZombiefiedProcess(cpl);
                        	walkList(cpl, isRunning);
							printPath(buf, pathLen);
							continue;
                        }

			int pid = fork();

			if(pid == -1){
				perror("Unable to start child process\n");
				exit(EXIT_FAILURE);
			}
			if(pid == 0){
				execvp(argList[0], argList);
				exit(errno);
			}else{
				if(bg){
					insertElement(cpl,pid,printStr);
					waitpid(pid, &status, WNOHANG);
					// KORREKTUR: hier wird keine ausgabe gemacht falls der prozess bereits fertig ist. Einfach weglassen, der prozess wird in der nächsten zombierunde mit eingesammelt (-0.5P)
				}else{
					waitpid(pid, &status, 0);
					fprintf(stderr, "Exitstatus [%s] = %d\n\n", printStr, WEXITSTATUS(status));
					// ANMERKUNG: hier WEXITSTATUS genutzt, aber nicht WIFEXITED überprüft.
				}
				/*struct qel* node = cpl->head;
				while(node != NULL){
					printf("[pid: %d | command: %s] ", node->pid, node->cmdLine);
					node = node->next;
				}*/
			}
		}

		findZombiefiedProcess(cpl);
		printPath(buf, pathLen);
	}

	free(buf);
	free(input);
	free(printStr);
	free(cpl);

	return 0;
}
// PUNKTE: 5.25

// ANMERKUNG: Ihr scheint Probleme mit dem Konzept des errnos und waitpid zu haben. Konnt damit gerne in die Gruppenübungen. Dort nehmen wir unf für jede eurer Fragen Zeit.

// TODO: helper implementation
