![I was lucky enough to know what was Winamp](https://cdn.theatlantic.com/thumbor/4z2POkjuJrgRnOXLyL9-ttxFJmA=/570x243/media/img/posts/2013/12/winamp/original.jpg)

> I was lucky enough to know what is Winamp :)

# Batch Playlist Tool

Playlist is a batch processing tool for playlists, songs and users.

## Quick Run
If you want a quick run please follow below commands. Otherwise jump to [Installation](https://github.com/ecaglar/Playlist/blob/main/README.md#installation) section

```
mkdir playlistApp
cd /playlistApp
git clone https://github.com/ecaglar/Playlist.git
mvn package
cd /target
java -jar playlist-0.0.1-SNAPSHOT.jar --input-file-name=../mixtape.json --change-file-name=../changes.json --output-file-name=../output.json
cd ..
```

## Installation

Use  [Git](https://git-scm.com/) to clone repository to your local

```bash
git clone https://github.com/ecaglar/Playlist.git
```

Use  [Maven](https://maven.apache.org/) to compile and install Playlist.

```bash
mvn package
```
By default, the jar file will be located under */target* folder in your project folder after executing the command.
For example, if you cloned the project under */home/playlist* then the jar file will be located at
```bash
 /home/playlist/target/playlist-0.0.1-SNAPSHOT.jar
 ```
## Usage

You should provide three files to the batch tool.
```
1. --input-file-name (Mix input file)
2. --change-file-name (Change file)
3. --output-file-name (Output file)
```

(Inside same directory with jar file)

```bash
  java -jar playlist-0.0.1-SNAPSHOT.jar --input-file-name=[path_to_input_file] --change-file-name=[path-to-change_file] --output-file-name=[path_to_output_file]
```

## Scaling

Current version of the program can deal with large files as it streams the file instead of reading all into the memory. This produces a min memory usage footprint. Beside streaming, it also applies changes directly on top of original data structures without creating another set of interim data structures just for change file. Generating the output file also works as same way with streams. GSON Streaming library has been used to maximize performance. 

Still, there is room for improvement especially with extremely large inut files or for a real time solution. Since we already use streaming for file instead of reading them into the memory at once, we can focus on improving the interim data structures we create and their memory consuption.

Instead of creating all interim data structures in the memory, we can leverage a better streaming solution such as [Kafka](https://kafka.apache.org/) and [KTable](https://www.confluent.io/blog/kafka-streams-tables-part-1-event-streaming) which also fits into the problem segment. Once memory usage is minimized the next question will be the high throughput. Below is the proposed solution for extremly large files and high throughput needs. 

![Scale](https://github.com/ecaglar/Playlist/blob/main/Scale2.png)

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)
