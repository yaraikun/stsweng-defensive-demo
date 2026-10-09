SRC_DIR = src
BIN_DIR = bin
DATA_DIR = data

JC = javac
JFLAGS = -d $(BIN_DIR) -sourcepath $(SRC_DIR)
JVM = java

SOURCES = $(wildcard $(SRC_DIR)/*.java)

ifeq ($(OS),Windows_NT)
    SHELL := cmd.exe
    fixpath = $(subst /,\,$1)
    MKDIR = if not exist "$(call fixpath,$1)" mkdir "$(call fixpath,$1)"
    RMDIR = if exist "$(call fixpath,$1)" rmdir /s /q "$(call fixpath,$1)"
    TOUCH = type nul > "$(call fixpath,$1)"
else
    MKDIR = mkdir -p $1
    RMDIR = rm -rf $1
    TOUCH = touch $1
endif

all: $(BIN_DIR)/.build_stamp

$(BIN_DIR)/.build_stamp: $(SOURCES) | $(BIN_DIR)
	$(JC) $(JFLAGS) $(SOURCES)
	@$(call TOUCH,$@)

$(BIN_DIR):
	$(call MKDIR,$(BIN_DIR))

run: all
	$(JVM) -cp $(BIN_DIR) Main

clean:
	$(call RMDIR,$(BIN_DIR))

.PHONY: all run clean