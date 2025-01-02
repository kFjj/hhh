# 代码结构及部件组成

## 1. Command 类

**功能：** 表示用户输入的命令。

### 属性：
- `commandWord`：命令的第一个单词（通常是动词）。
- `secondWord`：命令的第二个单词（通常是名词或方向）。

### 方法：
- `Command(String firstWord, String secondWord)`：构造函数，初始化命令和第二个单词。
- `getCommandWord()`：获取命令词。
- `getSecondWord()`：获取第二个单词。
- `isUnknown()`：检查命令是否未知（即 `commandWord` 是否为 `null`）。
- `hasSecondWord()`：检查是否有第二个单词。

---

## 2. CommandWords 类

**功能：** 管理有效的命令列表。

### 属性：
- `validCommands`：一个字符串数组，包含所有有效的命令词。

### 方法：
- `isCommand(String aString)`：检查给定的字符串是否是有效命令。
- `showAll()`：打印所有有效的命令。

---

## 3. Game 类

**功能：** 游戏的主要逻辑，包括房间创建、命令处理等。

### 属性：
- `parser`：解析器对象，用于解析用户输入。
- `currentRoom`：当前玩家所在的房间。

### 方法：
- `Game()`：构造函数，初始化房间并设置解析器。
- `createRooms()`：创建所有房间并设置它们之间的连接。
- `play()`：游戏主循环，处理用户输入直到游戏结束。
- `printWelcome()`：打印欢迎信息。
- `processCommand(Command command)`：处理用户输入的命令。
- `printHelp()`：打印帮助信息。
- `goRoom(Command command)`：根据命令移动到指定方向的房间。
- `quit(Command command)`：处理退出命令。

---

## 4. Main 类

**功能：** 游戏的入口点。

### 方法：
- `main(String[] args)`：启动游戏实例并开始游戏。

---

## 5. Parser 类

**功能：** 解析用户输入的命令。

### 属性：
- `commands`：命令词管理对象。
- `reader`：用于读取用户输入的 `Scanner` 对象。

### 方法：
- `Parser()`：构造函数，初始化命令词管理对象和 `Scanner` 对象。
- `getCommand()`：从用户输入中解析命令。
- `showCommands()`：显示所有有效的命令。

---

## 6. Room 类

**功能：** 表示游戏中的房间。

### 属性：
- `description`：房间的描述。
- `exits`：一个 `HashMap`，存储房间的出口方向及其对应的房间。

### 方法：
- `Room(String description)`：构造函数，初始化房间描述和出口映射。
- `setExit(String direction, Room neighbor)`：设置房间的出口。
- `getShortDescription()`：获取房间的简短描述。
- `getLongDescription()`：获取房间的详细描述，包括出口信息。
- `getExit(String direction)`：获取指定方向的出口房间。
# 类图
