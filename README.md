# Bulk Image Resizer v1.0.0

## Description
Bulk Image Resizer is a standalone Windows application built in Java that allows you to batch process images. With a user-friendly GUI, you can easily select input and output folders, apply center cropping and resizing, and add a custom filename prefix to the processed images. The installer is built using jpackage and includes a minimal Java runtime, so no separate Java installation is required.

## Key Features
- **User-friendly GUI:** Easily select folders and configure processing options.
- **Batch Processing:** Process multiple images at once.
- **Resize Images:** Scale images to your desired dimensions.
- **Center Crop Images:** Crop images from the center based on specified width and height.
- **Customizable Filenames:** Rename processed images with a user-defined prefix (e.g., `cropped_1.jpg`, `cropped_2.jpg`).
- **Standalone Installer:** Distribute a complete Windows installer (.exe) that bundles a Java runtime.

## Installation
1. **Download the Installer:**  
   Visit the [GitHub Releases](https://github.com/psrnk/bulk-image-resizer/releases) page and download the Bulk Image Resizer installer.
2. **Run the Installer:**  
   Double-click the installer (.exe). You might be prompted by Windows UAC to allow changes.
3. **Installation Location:**  
   The application will install to a default location (e.g., within your AppData or Program Files folder), and a shortcut will be created in your Start Menu and/or on your Desktop.

## Usage
1. **Launch the Application:**  
   Open Bulk Image Resizer from the Start Menu or Desktop shortcut.
2. **Configure Processing Options:**
    - **Input Folder:** Select the folder containing the images you wish to process.
    - **Output Folder:** Select the folder where processed images will be saved.
    - **Processing Options:**
        - Enable **Center Crop** and specify the crop width and height.
        - Enable **Resize** and specify the target width and height.
    - **Filename Prefix:** Enter a custom prefix (e.g., `cropped_1`) for the processed images.
3. **Process Images:**  
   Click **"Process Selected Images"** to begin processing. Processed images will be saved in the output folder with filenames like `cropped_nice_1.jpg`, `cropped_nice_2.jpg`, etc.

## Troubleshooting
- **No Visible Installation:** If the installer seems to do nothing after the UAC prompt, check the Start Menu or Desktop for a newly created shortcut.
- **Supported Image Formats:** Ensure your input folder contains supported image types (e.g., JPG, PNG, BMP, GIF).
- For further issues, please refer to the GitHub Issues page.

## License
This project is licensed under the MIT License. See the LICENSE file for full details.

## Contributing
Contributions, feedback, and bug reports are welcome. Please submit issues or pull requests via GitHub.

## Acknowledgements
Bulk Image Resizer is built using Java, Swing, and jpackage. It is bundled with a minimal Java runtime to provide a fully standalone experience on Windows.
