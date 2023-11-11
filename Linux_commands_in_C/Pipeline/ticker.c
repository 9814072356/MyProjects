// TODO: includes
#include  <stdio.h>
#include  <signal.h>
#include  <stdlib.h>
#include <errno.h>
#include <stdbool.h>
#include <string.h>
#include <unistd.h>
#include <sys/time.h>
#include <time.h>
// TODO: global variables
int laps = 1;
struct timeval t0; //start time
struct timeval tS; //shortest time
struct timeval tE; //elapsed time
struct timeval tSum; //sum of times
bool fin = false;
bool racing = true;
bool started = false;

// PUNKTE: 6

void INTSIG(int);
void TERMSIG(int);
// TODO: helpers, signal handlers
void INTSIG(int sig){ fin = true; }
void TERMSIG(int sig){ racing = false; }
bool isNum(char in[]){
	for(int i = 0; i < strlen(in); i++){
		if(in[i] < '0' || in[i] > '9')return false;
	}
	return true;
}

void SecToMin(int sec, int min){
        if(sec >= 60){
        	min = sec / 60;
		sec = sec - min * 60;
        }
	return;
}

// TODO: implement main
int main(int argc, char* argv[]){
	tS.tv_sec = 0;
	tS.tv_usec = 0;
	if(argc != 2 || !isNum(argv[1])){
		exit(EXIT_FAILURE);
	}
	int input = atoi(argv[1]);
	if(input == 0)exit(EXIT_FAILURE);
	pid_t pid = getpid();
	fprintf(stderr, "pid: %d\n", pid);
	fprintf(stderr, "ready, press Ctrl+C to start...\n");
	signal(SIGINT, INTSIG);
	signal(SIGQUIT, TERMSIG);
	while(laps <= input && racing){
		if(fin){
			if(!started){
				started = true;
				fprintf(stderr, "race started, press Ctrl+C for next round!\n");
				gettimeofday(&t0,NULL);
				tS = t0;
			}else{
				struct timeval buf;
				gettimeofday(&buf, NULL);
				timersub(&buf, &t0, &tE);
				t0 = buf;
				if(timercmp(&tE, &tS, <)){
					tS.tv_sec = tE.tv_sec;
					tS.tv_usec = tE.tv_usec;
				}
				int min = 0;
				int sec = tE.tv_sec;
				SecToMin(sec, min);
            			fprintf(stderr, "\nlap %03d: %02i:%02i.%ld\n", laps, min, sec, tE.tv_usec);
				laps++;
				timeradd(&tSum, &tE, &tSum);
			}
			fin = false;
		}
	}
	if(racing){
		int min = 0;
		int sec = tSum.tv_sec;
		SecToMin(sec, min);
		fprintf(stderr, "sum: %02i:%02i.%ld\n", min, sec, tSum.tv_usec);
        	min = 0;
		sec = tS.tv_sec;
		SecToMin(sec, min);
		fprintf(stderr, "fastest: %02i:%02i.%ld\n", min, sec, tS.tv_usec);
	}else{
		fprintf(stderr, "\nrace canceled\n");
	}
	return 0;
}


