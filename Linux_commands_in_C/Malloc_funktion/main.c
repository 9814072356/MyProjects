#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

#include "halde.h"

int main(int argc, char *argv[]) {
	halde_print();
	
	printf("malloc m1\n");
	char* m1 = halde_malloc(200*1024);
	halde_print();
	printf("malloc m2\n");
	char* m2 = halde_malloc(100*1024);
	halde_print();
	printf("malloc m3\n");
	char* m3 = halde_malloc(200*1024);
	halde_print();
	printf("malloc m4\n");
	char* m4 = halde_malloc(300*1024);
	halde_print();
	printf("malloc m5\n");
	char* m5 = halde_malloc(100*1024);
	halde_print();

	printf("free m2\n");
	halde_free(m2);
	halde_print();
	printf("free m4\n");
	halde_free(m4);
	halde_print();
	printf("free m3\n");
	halde_free(m3);
	halde_print();
	printf("free m1\n");
	halde_free(m1);
	halde_print();
	printf("malloc m6\n");
	char* m6 = halde_malloc(819248);
	halde_print();
	printf("free m5\n");
	halde_free(m5);
	halde_print();
	printf("free m6\n");
	halde_free(m6);
	halde_print();

	exit(EXIT_SUCCESS);
}
