# Playlist

Playlist is a batch processing tool for playlists, songs and users.

## Installation

Use the [Maven](https://maven.apache.org/) to compile and install Playlist.

```bash
mvn package
```
By default, the jar file will be located under /target folder in your project folder.
For example, if you cloned the project under /home/playlist then the jar fiel will be located at
```bash
 /home/playlist/target/playlist-0.0.1-SNAPSHOT.jar
 ```
## Usage

You should provide three files to the batch tool.
1. Mix input file
2. Change file
3. Output file

(Inside same directory with jar file)



```bash
  java -jar playlist-0.0.1-SNAPSHOT.jar --input-file=[path_to_input_file] --change-file-name=[path-to-change_file] --output-file-name=[path_to_output_file]
```

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)
