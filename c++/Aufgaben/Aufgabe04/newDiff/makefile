#########################################
# Makefile
#########################################

CXX = g++
CXXFLAGS:= -std=c++17 -Wall -Wextra -pedantic -g -O0 \
	-Winit-self -Wold-style-cast \
	-Wconversion -Wsign-conversion -Wlogical-op \
#	-Werror

EXEC	:= newdiff
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
	$(CXX) $(CXXFLAGS) -MM -MT '$(patsubst %.cpp,obj/%.o,$<)' $< -MF $@

# Compilation
obj/%.o: %.cpp obj/%.d
	@mkdir -p $(dir $@)
	$(CXX) $(CXXFLAGS) -c $< -o $@


# Link library and all object files to executable
$(EXEC)	: $(OBJS)
	$(CXX) $(OBJS) -o $@

# Documentation
doc	: $(SRCS) $(HDRS)
	doxygen

# Clean up directory
clean	: 
	rm -f $(EXEC)
	rm -rf obj
	rm -rf doc


