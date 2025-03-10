# Report

Submitted report to be manually graded. We encourage you to review the report as you read through the provided
code as it is meant to help you understand some of the concepts. 

## Technical Questions

1. What is the difference between == and .equals in java? Provide a code example of each, where they would return different results for an object. Include the code snippet using the hash marks (```) to create a code block.
   
   In java, == compares the memory address of two objects, while .equals compares the actual content such as attributes of two objects.
   
   ```java
   class Person {
      String name;
      Person(String name) {
         this.name = name;
   }
   
   @Override
   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null || getClass() != obj.getClass()) return false;
      Person person = (Person) obj;
      return name.equals(person.name);
   }
   
   public static void main(String[] args) {
   Person p1 = new Person("Chris");
   Person p2 = new Person("Chris");
   Person p3 = p1;
   
   System.out.println(p1 == p2);     // false, different memory locations
   System.out.println(p1.equals(p2)); // true, because names are the same
   System.out.println(p1 == p3);     // true, same memory location
   
   ```

2. Logical sorting can be difficult when talking about case. For example, should "apple" come before "Banana" or after? How would you sort a list of strings in a case-insensitive manner?
   
   I would use Comparator with String.CASE_INSENSITIVE_ORDER, or compareToIgnoreCase predefined method to sort a list of Strings in a case-insensitive manner.



3. In our version of the solution, we had the following code (snippet)
    ```java
    public static Operations getOperatorFromStr(String str) {
        if (str.contains(">=")) {
            return Operations.GREATER_THAN_EQUALS;
        } else if (str.contains("<=")) {
            return Operations.LESS_THAN_EQUALS;
        } else if (str.contains(">")) {
            return Operations.GREATER_THAN;
        } else if (str.contains("<")) {
            return Operations.LESS_THAN;
        } else if (str.contains("=="))...
    ```
    Why would the order in which we checked matter (if it does matter)? Provide examples either way proving your point. 
   
   The order does matter and we should check for the more specific(longer) operator first. For example ">=" and ">", we should check for ">=" first since it captures wider range of items. If we check ">" first, then ">=" would never be executed.


4. What is the difference between a List and a Set in Java? When would you use one over the other? 
   
   List allows duplicates and the elements can be accessed by index, while Set does not allow duplicates. If ordering and duplicates matter, then it's ideal to use List. If uniqueness matters, then it's ideal to use Set.



5. In [GamesLoader.java](src/main/java/student/GamesLoader.java), we use a Map to help figure out the columns. What is a map? Why would we use a Map here?
   
   A Map in Java is a collection that stores key-value pairs. Each key maps to exactly one value, and duplicate keys are not allowed. 
   The processHeader method in GamesLoader maps column names to their index positions. Using Map allows quickly look up the column index.



6. [GameData.java](src/main/java/student/GameData.java) is actually an `enum` with special properties we added to help with column name mappings. What is an `enum` in Java? Why would we use it for this application?
   
   `enum` is a data type used to define a fixed set of named constants. In GameData, each constant is a column name in the CSV file. We use this enum class to avoid hardcode every column String, and it also provides type safety.

   

7. Rewrite the following as an if else statement inside the empty code block.
    ```java
    switch (ct) {
                case CMD_QUESTION: // same as help
                case CMD_HELP:
                    processHelp();
                    break;
                case INVALID:
                default:
                    CONSOLE.printf("%s%n", ConsoleText.INVALID);
            }
    ``` 

    ```java
    // your code here, don't forget the class name that is dropped in the switch block..
   if (ct == CMD_QUESTION || ct == CMD_HELP) {
   processHelp();
   } else if (ct == INVALID) {
   CONSOLE.printf("%s%n", ConsoleText.INVALID);
   } else {
   CONSOLE.printf("%s%n", ConsoleText.INVALID);
   }
    ```

## Deeper Thinking

ConsoleApp.java uses a .properties file that contains all the strings
that are displayed to the client. This is a common pattern in software development
as it can help localize the application for different languages. You can see this
talked about here on [Java Localization – Formatting Messages](https://www.baeldung.com/java-localization-messages-formatting).

Take time to look through the console.properties file, and change some of the messages to
another language (probably the welcome message is easier). It could even be a made up language and for this - and only this - alright to use a translator. See how the main program changes, but there are still limitations in 
the current layout. 

Post a copy of the run with the updated languages below this. Use three back ticks (```) to create a code block. 

```text
The following commands are available:
exit - exit the program
help or ? [list | filter] - show this help message, Options list - show help for the list command, filter - show help for the filter command.

// your consoles output here
以下指令可用：
exit - 退出程式
help 或 ? [list | filter] - 顯示此說明訊息，選項：list - 顯示 list 指令的說明，filter - 顯示 filter 指令的說明
```

Now, thinking about localization - we have the question of why does it matter? The obvious
one is more about market share, but there may be other reasons.  I encourage
you to take time researching localization and the importance of having programs
flexible enough to be localized to different languages and cultures. Maybe pull up data on the
various spoken languages around the world? What about areas with internet access - do they match? Just some ideas to get you started. Another question you are welcome to talk about - what are the dangers of trying to localize your program and doing it wrong? Can you find any examples of that? Business marketing classes love to point out an example of a car name in Mexico that meant something very different in Spanish than it did in English - however [Snopes has shown that is a false tale](https://www.snopes.com/fact-check/chevrolet-nova-name-spanish/).  As a developer, what are some things you can do to reduce 'hick ups' when expanding your program to other languages?

As a reminder, deeper thinking questions are meant to require some research and to be answered in a paragraph for with references. The goal is to open up some of the discussion topics in CS, so you are better informed going into industry. 


Localization matters in different aspects. In addition to market share, localization also helps enhance the user experience, as users are more likely to engage with software that speaks their language. Also, different regions have unique laws and regulations, and localization helps ensure that software complies with local standards, avoiding legal pitfalls.[^1]

Risks of doing localization wrong can lead to offense or alienation of the target audience, damage the brand's image, or even worse—legal issues if local laws and regulations are not adhered to.

I would engage native speakers and cultural experts to ensure content resonates appropriately with local audiences and consult with the Legal & Compliance team to ensure we comply with local laws and regulations during the localization process.

[^1] Localization vs. Internationalization Testing Guide. https://testrigor.com/blog/localization-vs-internationalization-testing-guide/. Accessed: 2025-03-09.