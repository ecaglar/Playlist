![I was lucky enough to know what was Winamp](https://cdn.theatlantic.com/thumbor/4z2POkjuJrgRnOXLyL9-ttxFJmA=/570x243/media/img/posts/2013/12/winamp/original.jpg)

> I was lucky enough to know what is Winamp :)

# Batch Playlist Tool

Playlist is a batch processing tool for playlists, songs, and users.

1. If you try to add a new playlist with the same id then it will be discarded
2. Remove playlist operation executed last. If you have add and delete playlist actions then it will be created first then deleted before writing to the output file
3. Adding songs to playlist action happens only both song and playlist exist
4. If the format of the files is wrong then the program will exit with the proper error message.


## Quick Run
If you want a quick run please follow the below commands. Otherwise jump to [Installation](https://github.com/ecaglar/Playlist/blob/main/README.md#installation) section

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

Use  [Git](https://git-scm.com/) to clone the repository to your local

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
 
## Change file format

Change file is simply a json file that looks very similar to the original input file. Playlist add, remove and song add actions defined.
**Add** and **Remove** actions are defined under **Playlist** field. 

```json
{
  "playlists" : {
    "add" : [
      {
        "id" : "4",
        "user_id" : "2",
        "song_ids" : [
          "2",
          "3"
        ]
      },
      {
        "id" : "5",
        "user_id" : "3",
        "song_ids" : [
          "11",
          "14",
          "16"
        ]
      }
    ],
    "remove":["1","5"]
  },
  "songs" : [
    {
      "song_id":"2",
      "playlist_id" : "4"
    },
    {
      "song_id":"35",
      "playlist_id" : "5"
    },
    {
      "song_id":"35",
      "playlist_id" : "6"
    }
  ]
}

```

## Usage

You should provide three files to the batch tool.
```
1. --input-file-name (Mix input file)
2. --change-file-name (Change file)
3. --output-file-name (Output file)
```

(Inside the same directory with jar file)

```bash
  java -jar playlist-0.0.1-SNAPSHOT.jar --input-file-name=[path_to_input_file] --change-file-name=[path-to-change_file] --output-file-name=[path_to_output_file]
```

## Scaling

The current version of the program can **deal with large files as it streams the file instead of reading it all into the memory**. This produces a min memory usage footprint. Besides streaming, it also applies changes directly on top of original data structures without creating another set of interim data structures just for the change file. Generating the output file also works as same way with streams. GSON Streaming library has been used to maximize performance. 

Still, there is room for improvement especially with extremely large input files or for a real-time solution. Since we already use streaming for a file instead of reading them into the memory at once, we can focus on improving the interim data structures we create and their memory consumption.

Instead of creating all interim data structures in the memory, we can leverage a better streaming solution such as [Kafka](https://kafka.apache.org/) and [KTable](https://www.confluent.io/blog/kafka-streams-tables-part-1-event-streaming) which also fits into the problem segment. Once memory usage is minimized the next question will be the high throughput. Below is the proposed solution for extremely large files and high throughput needs. 

The logic here is that, since user and song data don't change we can stream and write them to output file directly. However, we need to process Playlists since they can be added, removed, or updated with new songs. In this case, instead of immutable stream series, what we nee is that, the latest snapshot of the playlist object whenever a change happens. This can be done through KTables as the object with the same key can be aggregated and updated accordingly. 

One additional thing should be done here is that, we need to assign unique ids to each playlist, user and song. Only then they can be written to related partition with the partition ordering guarenteed. 

**Note**: One challenge should be solved here which is remove playlist order. Since it will be written to the partition in the order read from the file, we can not guarentee that remove action will be executed as a last action. Moving remove playlist actions as a different topic to a different partition and waiting for all other consumers to finish their work might be one of the solution.

![Scale](https://github.com/ecaglar/Playlist/blob/main/Scale2.png)

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)
