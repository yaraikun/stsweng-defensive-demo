SRC_DIR = src
BIN_DIR = bin
DATA_DIR = data

JC = javac
JFLAGS = -d $(BIN_DIR) -sourcepath $(SRC_DIR)
JVM = java

SOURCES = $(wildcard $(SRC_DIR)/*.java)
CLASSES = $(patsubst $(SRC_DIR)/%.java, $(BIN_DIR)/%.class, $(SOURCES))

all: $(BIN_DIR) $(CLASSES)

$(BIN_DIR):
	mkdir -p $(BIN_DIR)

$(CLASSES): $(SOURCES) | $(BIN_DIR)
	$(JC) $(JFLAGS) $(SOURCES)

run: all
	@if [ -f $(DATA_DIR)/events.csv ]; then cp $(DATA_DIR)/events.csv .; fi
	$(JVM) -cp $(BIN_DIR) Main

clean:
	rm -rf $(BIN_DIR) events.csv

.PHONY: all run clean
