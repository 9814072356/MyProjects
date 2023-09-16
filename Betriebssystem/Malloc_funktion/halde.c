#include "halde.h"
#include <stdlib.h>
#include <string.h>
#include <errno.h>
#include <stdio.h>
#include <stdint.h>

/// Magic value for occupied memory chunks.
#define MAGIC ((void*)0xbaadf00d)

/// Size of the heap (in bytes).
#define SIZE (1024*1024*1)

/// Memory-chunk structure.
struct mblock {
        struct mblock *next;
        size_t size;
        char memory[];
};

/// Heap-memory area.
char memory[SIZE];

/// Pointer to the first element of the free-memory list.
static struct mblock *head;

/// Helper function to visualise the current state of the free-memory list.
void halde_print(void) {
        struct mblock* lauf = head;

        // Empty list
        if ( head == NULL ) {
                fprintf(stderr, "(empty)\n");
                return;
        }

        // Print each element in the list
        fprintf(stderr, "HEAD:  ");
        while ( lauf ) {
                fprintf(stderr, "(addr: 0x%08zx, off: %7zu, ", (uintptr_t) lauf, (uintptr_t)lauf - (uintptr_t)memory);
                fflush(stderr);
                fprintf(stderr, "size: %7zu)", lauf->size);
                fflush(stderr);

                if ( lauf->next != NULL ) {
                        fprintf(stderr, "\n  -->  ");
                        fflush(stderr);
                }
                lauf = lauf->next;
        }
        fprintf(stderr, "\n");
        fflush(stderr);
}

struct mblock* findMBlock(size_t mem){
        // ANMERKUNG: the list of mBlocks is a easy way to itterate over the free mBlocks
        struct mblock* temp = (struct mblock*)memory;
        while(temp->memory + temp->size < memory + SIZE){
                if(temp->next == NULL && temp->size >= mem){
                        return temp;
                }
                temp = (struct mblock*)(temp->memory + temp->size);
        }
        return NULL;
}

size_t mblockSIZE = sizeof(struct mblock);

void *halde_malloc (size_t size) {
        // TODO: implement me!
        if(head == NULL){
                head = (struct mblock*)memory;
                head->next = NULL;
                head->size = SIZE - mblockSIZE;
        }

        struct mblock* retMemPt;

        if(size == 0 || head == (struct mblock*)(memory + SIZE) || head->size == 0){
                printf("Memory too small for me to care.\n");
                errno = ENOMEM;
                // KORREKTUR: if size == 0 than no errno must be set. ENOMEM means that not enough memory was available. (-0.5P)
                return NULL;
        }

        if(head->memory + size != memory + SIZE){
                if(size + mblockSIZE > head->size){
                        struct mblock* block = findMBlock(size + mblockSIZE);
                        // KORREKTUR: you could giveaway a block with a size between size and size + mBlockSIZE. don´t create a new mBlock and leave block->size untouched so you would not forget that you give>
                        if(block == NULL){
                                errno = ENOMEM;
                                printf("MISSION FAILED!!! ABORT!!!\n");
                                return NULL;
                        }else{
                                size_t prevSize = block->size;
                                head = block;
                                head->next = MAGIC;
                                head->size = size;
                                head = (struct mblock*)(head->memory + head->size);
                                head->next = NULL;
                                head->size = prevSize - size;
                                // KORREKTUR: the new mBlock take some space as well so the right calculated size would be prevSize - size - mBlockSIZE (-1P)
                        }
                }else{
                        size_t prevSize = head->size;
                        retMemPt = head;
                        head->next = MAGIC;
                        head->size = size;
                        head = (struct mblock*)(head->memory + head->size);
                        printf("head size: %ld\n",head->size);
                        head->next = NULL;
                        head->size = prevSize - size-mblockSIZE;
                }
        }else{
                retMemPt = head;
                head->next = MAGIC;
                head->size = size;
                head = (struct mblock*)(head->memory + head->size);
        }

        return retMemPt->memory;
}
// PUNKTE: 1.5

void mergeAllFreeBlocks(){
        struct mblock* start = (struct mblock*)memory;
        struct mblock* end = (struct mblock*)(start->memory + start->size);
        while(end->memory + end->size <= memory + SIZE){
                if(start->next == MAGIC){
                        start = (struct mblock*)(start->memory + start->size);
                        end = (struct mblock*)(end->memory + end->size);
                                }else{
                        while(end->next != MAGIC){
                                start->size = start->size + end->size + mblockSIZE;
                                if(head-end == 0){
                                        head = start;
                                }
                                size_t prevSize = end->size;
                                end->size = 0;
                                if(end->memory + prevSize == memory + SIZE){
                                        return;
                                }
                                end = (struct mblock*)(end->memory + prevSize);
                        }
                        start = end;
                        end = (struct mblock*)(end->memory + end->size);
                }
        }
}
// ANMERKUNG: It would have been enough to check the block before and after the inserted block. Allways checking the whole memory is not efficent and more complex. But this implementation seems to work. :)
// PUNKTE: 4

void halde_free (void *ptr) {
        // TODO: implement me!
        if(ptr == NULL || ptr < (void*)memory || ptr > (void*)(memory + SIZE)){
                printf("Yo what you put in there is illegal!!!\n");
                return;
        }
        if(head == NULL){
                errno = ENOMEM;
                printf("Sorry my hands are tied. Not enough power to free a null pointer.\n");
                return;
                // KORREKTUR: free does not set any errnos (-0.5P)
        }
        head = (struct mblock*)((char*)ptr - mblockSIZE);
        if(head->next == NULL){
                // KORREKTUR: the only case where the returned pointer is allwowed is when next == MAGIC. (-1P)
                printf("Maybe you forgot something. Something like .... allocating some memory in this space!!!\n");
                abort();
                return;
        }
        head->next = NULL;
        memset(head->memory,'\0',head->size);
        mergeAllFreeBlocks();

        return;
}

// PUNKTE: 1.5

// ANMERKUNG: the prints looks like you had fun implementing this. but please comment them out before the submit. libary-function like this schould not print something. image the real malloc would do this :D
