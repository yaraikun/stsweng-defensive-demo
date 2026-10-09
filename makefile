SRC_DIR = src
BIN_DIR = bin
DATA_DIR = data

JC = javac
JFLAGS = -d $(BIN_DIR) -sourcepath $(SRC_DIR)
JVM = java

SOURCES = $(wildcard $(SRC_DIR)/*.java)

all: $(BIN_DIR)/.build_stamp

$(BIN_DIR)/.build_stamp: $(SOURCES) | $(BIN_DIR)
	$(JC) $(JFLAGS) $(SOURCES)
	@touch $@

$(BIN_DIR):
	mkdir -p $(BIN_DIR)

run: all
	$(JVM) -cp $(BIN_DIR) Main

clean:
	rm -rf $(BIN_DIR)

.PHONY: all run clean
