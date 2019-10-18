using System;
using System.IO;

namespace dev275x.studentlist
{
    class Program
    {
        static void Main(string[] args)
        {

            if (args == null || args.Length != 1)
            {
                Console.WriteLine("Usage: dotnet dev275x.rollcall.dll (a | r | c | +WORD | ?WORD)");
                return;
            }

            var fileContents = LoadData("students.txt");

            if (args[0] == "a") 
            {
                var words = fileContents.Split(',');
                foreach(var word in words) 
                {
                    Console.WriteLine(word);
                }
            }
            else if (args[0]== "r")
            {
                var words = fileContents.Split(',');
                var rand = new Random();
                var randomIndex = rand.Next(0,words.Length);
                Console.WriteLine(words[randomIndex]);
            }
            else if (args[0].Contains("+"))
            {
                var argValue = args[0].Substring(1);

                UpdateContent(fileContents + "," + argValue, "students.txt");
            }
            else if (args[0].Contains("?"))
            {
                var words = fileContents.Split(',');
                bool done = false;
                var argValue = args[0].Substring(1);
                for (int idx = 0; idx < words.Length && !done; idx++)
                {
                    if (words[idx] == argValue)
                        Console.WriteLine("We found it!");
                        done = true;
                }
            }
            else if (args[0].Contains("c"))
            {
                var characters = fileContents.ToCharArray();
                var in_word = false;
                var count = 0;
                foreach(var c in characters)
                {
                    if (c > ' ' && c < 0177)
                    {
                        if (!in_word) 
                        {
                            count = count + 1;
                            in_word = true;
                        }
                    }
                    else 
                    {
                        in_word = false;
                    }
                }
                Console.WriteLine(String.Format("{0} words found", count));
            }
        }


        static string LoadData(string fileName)
        {
            string line;
            using (var fileStream = new FileStream(fileName,FileMode.Open))
            using (var reader = new StreamReader(fileStream))
            {
                line = reader.ReadLine();
            }
            
            return line;
        }
        static void UpdateContent(string content, string fileName)
        {
            var now = DateTime.Now;
            var timestamp = String.Format("List last updated on {0}", now);
            using (var fileStream = new FileStream(fileName,FileMode.Open))
            using (var writer = new StreamWriter(fileStream))
            {
                writer.WriteLine(content);
                writer.WriteLine(timestamp);
            }
        }
    }
}