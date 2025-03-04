package com.imageresizer;

import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * A GUI-based Bulk Image Resizer with optional center cropping, resizing,
 * and filename prefixing. Uses the system look-and-feel for a cleaner look.
 */
public class ImageResizerGUI extends JFrame implements ActionListener {
    // Folder fields
    private JTextField inputFolderField, outputFolderField;

    // Buttons
    private JButton browseInputButton, browseOutputButton, loadImagesButton, startButton;

    // Image list
    private JList<String> imageList;
    private DefaultListModel<String> imageListModel;

    // Crop & Resize controls
    // Note: Only crop width and height are needed for center cropping.
    private JCheckBox enableCropCheck, enableResizeCheck;
    private JTextField cropWidthField, cropHeightField;
    private JTextField resizeWidthField, resizeHeightField;

    // Prefix controls
    private JTextField prefixField;

    public ImageResizerGUI() {
        super("Bulk Image Resizer by 0xP1P0");

        // Set system look-and-feel for a more native look
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        try {
            Image icon = Toolkit.getDefaultToolkit()
                    .getImage(getClass().getResource("/com/imageresizer/resources/app_icon.png"));
            setIconImage(icon);
        } catch (Exception e) {
            System.err.println("Could not load custom icon: " + e.getMessage());
        }


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // ============= TOP PANEL =============
        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Input folder
        gbc.gridx = 0;
        gbc.gridy = 0;
        topPanel.add(new JLabel("Input Folder:"), gbc);

        inputFolderField = new JTextField();
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        topPanel.add(inputFolderField, gbc);

        browseInputButton = new JButton("Browse");
        gbc.gridx = 2;
        gbc.weightx = 0;
        topPanel.add(browseInputButton, gbc);
        browseInputButton.addActionListener(this);

        // Output folder
        gbc.gridx = 0;
        gbc.gridy = 1;
        topPanel.add(new JLabel("Output Folder:"), gbc);

        outputFolderField = new JTextField();
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        topPanel.add(outputFolderField, gbc);

        browseOutputButton = new JButton("Browse");
        gbc.gridx = 2;
        gbc.weightx = 0;
        topPanel.add(browseOutputButton, gbc);
        browseOutputButton.addActionListener(this);

        // Load Images button
        loadImagesButton = new JButton("Load Images");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        topPanel.add(loadImagesButton, gbc);
        loadImagesButton.addActionListener(this);

        add(topPanel, BorderLayout.NORTH);

        // ============= CENTER PANEL =============
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        // Image list
        imageListModel = new DefaultListModel<>();
        imageList = new JList<>(imageListModel);
        imageList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane scrollPane = new JScrollPane(imageList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Select Images to Process"));
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // ============= OPTIONS PANEL (Crop/Resize/Prefix) =============
        JPanel optionsPanel = new JPanel(new GridBagLayout());
        optionsPanel.setBorder(BorderFactory.createTitledBorder("Options"));
        GridBagConstraints ogbc = new GridBagConstraints();
        ogbc.insets = new Insets(5, 5, 5, 5);
        ogbc.fill = GridBagConstraints.HORIZONTAL;

        // Enable Center Crop
        enableCropCheck = new JCheckBox("Enable Center Crop");
        ogbc.gridx = 0;
        ogbc.gridy = 0;
        ogbc.gridwidth = 2;
        optionsPanel.add(enableCropCheck, ogbc);

        // Crop Width
        ogbc.gridx = 0;
        ogbc.gridy = 1;
        ogbc.gridwidth = 1;
        optionsPanel.add(new JLabel("Crop Width:"), ogbc);
        cropWidthField = new JTextField("100");
        ogbc.gridx = 1;
        optionsPanel.add(cropWidthField, ogbc);

        // Crop Height
        ogbc.gridx = 0;
        ogbc.gridy = 2;
        optionsPanel.add(new JLabel("Crop Height:"), ogbc);
        cropHeightField = new JTextField("100");
        ogbc.gridx = 1;
        optionsPanel.add(cropHeightField, ogbc);

        // Spacer
        ogbc.gridx = 0;
        ogbc.gridy = 3;
        ogbc.gridwidth = 2;
        optionsPanel.add(new JSeparator(), ogbc);

        // Enable Resize
        enableResizeCheck = new JCheckBox("Enable Resize");
        ogbc.gridx = 0;
        ogbc.gridy = 4;
        ogbc.gridwidth = 2;
        optionsPanel.add(enableResizeCheck, ogbc);

        // Resize Width
        ogbc.gridx = 0;
        ogbc.gridy = 5;
        ogbc.gridwidth = 1;
        optionsPanel.add(new JLabel("Target Width:"), ogbc);
        resizeWidthField = new JTextField("800");
        ogbc.gridx = 1;
        optionsPanel.add(resizeWidthField, ogbc);

        // Resize Height
        ogbc.gridx = 0;
        ogbc.gridy = 6;
        optionsPanel.add(new JLabel("Target Height:"), ogbc);
        resizeHeightField = new JTextField("600");
        ogbc.gridx = 1;
        optionsPanel.add(resizeHeightField, ogbc);

        // Spacer
        ogbc.gridx = 0;
        ogbc.gridy = 7;
        ogbc.gridwidth = 2;
        optionsPanel.add(new JSeparator(), ogbc);

        // Prefix
        ogbc.gridx = 0;
        ogbc.gridy = 8;
        ogbc.gridwidth = 1;
        optionsPanel.add(new JLabel("Filename Prefix:"), ogbc);
        prefixField = new JTextField("filename");
        ogbc.gridx = 1;
        optionsPanel.add(prefixField, ogbc);

        centerPanel.add(optionsPanel, BorderLayout.EAST);
        add(centerPanel, BorderLayout.CENTER);

        // ============= BOTTOM PANEL =============
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        startButton = new JButton("Process Selected Images");
        startButton.addActionListener(this);
        bottomPanel.add(startButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == browseInputButton) {
            chooseInputFolder();
        } else if (e.getSource() == browseOutputButton) {
            chooseOutputFolder();
        } else if (e.getSource() == loadImagesButton) {
            loadImages();
        } else if (e.getSource() == startButton) {
            processSelectedImages();
        }
    }

    /**
     * Allows the user to choose the input folder.
     */
    private void chooseInputFolder() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int option = chooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File selected = chooser.getSelectedFile();
            if (selected != null) {
                inputFolderField.setText(selected.getAbsolutePath());
            }
        }
    }

    /**
     * Allows the user to choose the output folder.
     */
    private void chooseOutputFolder() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int option = chooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File selected = chooser.getSelectedFile();
            if (selected != null) {
                outputFolderField.setText(selected.getAbsolutePath());
            }
        }
    }

    /**
     * Loads image files from the input folder into the list.
     */
    private void loadImages() {
        imageListModel.clear();

        String inputPath = inputFolderField.getText().trim();
        if (inputPath.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a valid input folder.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        File inputDir = new File(inputPath);
        if (!inputDir.exists() || !inputDir.isDirectory()) {
            JOptionPane.showMessageDialog(this, "Invalid input folder: " + inputPath, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        File[] files = inputDir.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (isImageFile(file)) {
                imageListModel.addElement(file.getAbsolutePath());
            }
        }
    }

    /**
     * Processes the selected images with the chosen options (center crop, resize, prefix).
     */
    private void processSelectedImages() {
        String outputPath = outputFolderField.getText().trim();
        if (outputPath.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a valid output folder.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        File outputDir = new File(outputPath);
        if (!outputDir.exists()) {
            if (!outputDir.mkdirs()) {
                JOptionPane.showMessageDialog(this, "Failed to create output folder: " + outputPath, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // Crop parameters
        boolean doCrop = enableCropCheck.isSelected();
        int cropW = 0, cropH = 0;
        if (doCrop) {
            try {
                cropW = Integer.parseInt(cropWidthField.getText());
                cropH = Integer.parseInt(cropHeightField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Crop width and height must be integers.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // Resize parameters
        boolean doResize = enableResizeCheck.isSelected();
        int targetWidth = 0, targetHeight = 0;
        if (doResize) {
            try {
                targetWidth = Integer.parseInt(resizeWidthField.getText());
                targetHeight = Integer.parseInt(resizeHeightField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Resize width and height must be integers.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // Filename prefix
        String prefix = prefixField.getText().trim();

        // Get selected images
        java.util.List<String> selectedImages = imageList.getSelectedValuesList();
        if (selectedImages.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select at least one image to process.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Process each selected image
        int counter = 1;
        for (String imagePath : selectedImages) {
            File file = new File(imagePath);
            try {
                BufferedImage originalImage = ImageIO.read(file);
                if (originalImage == null) {
                    JOptionPane.showMessageDialog(this, "Could not read image: " + file.getName(), "Error", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                BufferedImage processedImage = originalImage;

                // Center Crop if enabled
                if (doCrop) {
                    processedImage = ImageResizer.cropToCenter(processedImage, cropW, cropH);
                }

                // Resize if enabled
                if (doResize) {
                    processedImage = ImageResizer.resize(processedImage, targetWidth, targetHeight);
                }

                // Determine extension from original filename
                String extension = getFileExtension(file.getName());
                if (extension.isEmpty()) {
                    extension = "jpg"; // default to jpg if no extension
                }

                // Construct new file name with prefix and counter
                String newFileName;
                if (!prefix.isEmpty()) {
                    newFileName = prefix + "_" + counter + "." + extension;
                } else {
                    newFileName = file.getName();
                }

                File outputFile = new File(outputDir, newFileName);
                ImageIO.write(processedImage, extension, outputFile);
                counter++;

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error processing image: " + file.getName() + "\n" + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        JOptionPane.showMessageDialog(this, "Processing completed.", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Checks if the file is an image based on its extension.
     */
    private boolean isImageFile(File file) {
        String[] imageExtensions = { "jpg", "jpeg", "png", "bmp", "gif" };
        String fileName = file.getName().toLowerCase();
        for (String ext : imageExtensions) {
            if (fileName.endsWith("." + ext)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Extracts the file extension from a file name.
     */
    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex != -1 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1).toLowerCase();
        }
        return "";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ImageResizerGUI gui = new ImageResizerGUI();
            gui.setVisible(true);
        });
    }
}
