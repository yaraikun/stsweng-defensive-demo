SRC_DIR = src
BIN_DIR = bin
DATA_DIR = data

JC = javac
JFLAGS = -d $(BIN_DIR) -sourcepath $(SRC_DIR)

SOURCES = $(shell find $(SRC_DIR) -name "*.type" 2>/dev/null || find $(SRC_DIR) -name "*.java")
CLASSES = $(patsubst $(SRC_DIR)/%.java, $(BIN_DIR)/%.class, $(SOURCES))

all: $(CLASSES)

$(BIN_DIR)/%.class: $(SRC_DIR)/%.java
	@mkdir -p $(BIN_DIR)
	$(JC) $(JFLAGS) $<

clean:
	rm -rf $(BIN_DIR)

.PHONY: all clean

