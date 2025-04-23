# Code Formatting
The below rules are the main coding styles implemented in this repo. Checkstyle
will enforce these at **test-compile** time:

- Max line length of 120 characters

- Package names must be lowercase

- All methods and constructors need a doc string 
```*.java
/**
 * Does thing but returns nothing
 */
void myFunc()
```

- Parameters for methods must start with lowercase letter
```*.java
// PASS
void myFunc(int myNum);
void myFunc(int nums);

// FAIL
void myFunc(int MYNUM);
void myFunc(int Nums);
```

- Parameters for methods can not be reassigned
```*.java
// FAIL
  void myFunc(int a) {
    a = 4;
    }
```

- Static and final (constant) variables must be UPPERCASE. Except loggers
```*.java
// PASS
static final String MY_VAR;

// FAIL
static final String myVar;
static final String myvar;
```

- Method/constructor names must be lowerCamelCase
```*.java
// PASS
void getVar();

// FAIL
void GETVAR();
void getvar();
```

- Default comes last in switch statement
```*.java
switch (expression) {
  case value1:
    // code
    break;
  case value2:
    // code
    break;
  ...
  ...
  default:
    // default statements
  }
```