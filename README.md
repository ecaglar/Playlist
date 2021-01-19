# Playlist

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

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)
