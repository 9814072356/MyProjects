#include <stdio.h>
#include <stdlib.h>
#include "plist.h"


void walkList(list *list, int (*callback) (pid_t, const char *) ) {
	// TODO: implement me, Subtask d
	// KORREKTUR: list nicht auf NULL geprüft (-0.25P)
	struct qel* e = list->head;
	int count = 0;
	printf("running process: ");
	while(e != NULL){
		int retVal = callback(e->pid, e->cmdLine);
		if(retVal != 0){
			break;
		}
		count++;
		e = e->next;
	}
	if(count == 0)printf("[ ^o^ ]");
	printf("\n\n");
}
// ANMERKUNG: Prints sind für einen Kunden im Produktivbetrieb sicher störend :)
// PUNKTE: 0.75

