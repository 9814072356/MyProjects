#########################################
# Makefile
#########################################

CXX = g++
CXXFLAGS:= -std=c++17 -Wall -Wextra -pedantic -g -O0 \
	-Winit-self -Wold-style-cast \
	-Wconversion -Wsign-conversion -Wlogical-op \
#	-Werror

EXEC	:= labyrinth
SRCS	:= $(wildcard src/*.cpp)
HDRS	:= $(wildcard src/*.h)
OBJS	:= $(patsubst %.cpp, obj/%.o, $(SRCS))
DEPS	:= $(patsubst %.o, %.d, $(OBJS))

# Default target
$(EXEC)	:

# Create all
all	: $(EXEC)
#all	: $(EXEC) lib doc

# Include existing dependancies
-include $(DEPS)

# Create dependancy files
.SUFFIXES += .d
obj/%.d: %.cpp
	@mkdir -p $(dir $@)
	$(CXX) $(CXXFLAGS) -MM -MT '$(patsubst src/%.cpp,obj/%.o,$<)' $< -MF $@

# Compilation
obj/%.o: %.cpp obj/%.d
	@mkdir -p $(dir $@)
	$(CXX) $(CXXFLAGS) -c $< -o $@

# Link a static library
LIB_OBJS:= obj/src/labyrinth_internal.o
LIB_NAME:= labyrinth
lib	: lib$(LIB_NAME).a
lib$(LIB_NAME).a	: $(LIB_OBJS)
	ar rcs $@ $(LIB_OBJS) 

# Link all object files to executable
$(EXEC)	: $(OBJS)
	$(CXX) $(OBJS) -L. -l$(LIB_NAME) -no-pie -o $@

# Documentation
doc	: $(SRCS) $(HDRS)
	doxygen

# Clean up directory
clean	: 
	rm -f $(EXEC) 
	rm -rf obj
	rm -rf doc


