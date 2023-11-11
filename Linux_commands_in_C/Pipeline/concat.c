// TODO: includes
#define READ 0
#define WRITE 1
#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <stdbool.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <string.h>
#include <fcntl.h>
// TODO: globals
// TODO: helpers
bool isNum(char in[]){
        for(int i = 0; i < strlen(in); i++){
                if(in[i] < '0' || in[i] > '9')return false;
        }
        return true;
}

// PUNKTE: 6

// seq 1 MAX | awk AWK_VORSCHRIFT | grep GREP_VORSCHRIFT > OUTPUT_FILE

int main(int argc, char *argv[]) {
// TODO: implement me
	if(argc < 4 || argc > 5){
		errno = EINVAL;
		fprintf(stderr, "%s\nMust have arguments in this form\n./concat MAX AWK_VORSCHRIFT GREP_VORSCHRIFT OUTPUT_FILE\n",strerror(errno));
		exit(EXIT_FAILURE);
	}
	char* seqPro[] = {"seq", "2", argv[1], NULL};
	char* awkPro[] = {"awk", argv[2], NULL};
	char* grepPro[] = {"grep", argv[3], NULL};
	int seqPipe[2];
	if(pipe(seqPipe) == -1){
		fprintf(stderr, "Bad pipe at seqPipe\n");
		exit(EXIT_FAILURE);
	}
	int seqpid = fork();
	if(seqpid == -1){
		fprintf(stderr, "Bad fork at seqpid\n");
		exit(EXIT_FAILURE);
	}else if(seqpid == 0){
		if(!isNum(argv[1])){
			errno = EINVAL;
			fprintf(stderr, "%s\n", strerror(errno));
			exit(EXIT_FAILURE);
		}else{
			dup2(seqPipe[1], STDOUT_FILENO);
			close(seqPipe[0]);
			close(seqPipe[1]);
			execvp(seqPro[0], seqPro);
			exit(1); //K
		}
	}
	close(seqPipe[1]);

	int awkPipe[2];
	if(pipe(awkPipe) == -1){
        fprintf(stderr, "Bad pipe at awkPipe\n");
        exit(EXIT_FAILURE);
    }
	int awkpid = fork();
	if(awkpid == -1){
		fprintf(stderr, "Bad fork at awkpid\n");
        exit(EXIT_FAILURE);
	}else if(awkpid == 0){
		dup2(seqPipe[0], STDIN_FILENO);
		dup2(awkPipe[1], STDOUT_FILENO);
		close(seqPipe[1]);
		close(awkPipe[0]);
		close(seqPipe[0]);
		close(awkPipe[1]);
		execvp(awkPro[0], awkPro);
		exit(1); //K
	}
	close(seqPipe[0]);
	close(awkPipe[1]);

	int greppid = fork();
	if(greppid == -1){
		fprintf(stderr, "Bad fork at greppid\n");
        exit(EXIT_FAILURE);
	}else if(greppid == 0){
		dup2(awkPipe[0], STDIN_FILENO);
		if(argv[4] != NULL && strcmp("-", argv[4]) != 0){
			int fd;
            mode_t mode = S_IWUSR | S_IRUSR | S_IRGRP;
            if((fd = creat(argv[4], mode)) < 0){
                perror("creat() error");
            }
            dup2(fd, STDOUT_FILENO);
			close(fd);
		}
        close(awkPipe[1]);
        close(seqPipe[0]);
        close(seqPipe[1]);
		close(awkPipe[0]);
		execvp(grepPro[0], grepPro);
		exit(1); //K
	}

	close(awkPipe[0]);
	
	// KORREKTUR: concat.c) Die Waitpid befehle nach unten im main thread zu verschieben löst das Warten auf superflous files in den Testfällen. (-0.25P)
	// PUNKTE: -0.25
	waitpid(seqpid, NULL, 0);
	waitpid(awkpid, NULL, 0);
	waitpid(greppid, NULL, 0);

	return 0;
}


