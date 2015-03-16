package com.intellij.openapi.project;

import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.vfs.VirtualFile;

/**
 * Author: dmitrylomov
 */
public class ProjectCoreUtil {
  public static final String DIRECTORY_BASED_PROJECT_DIR = Project.DIRECTORY_STORE_FOLDER;
  private static final String PROJECT_DIR_PATTERN = "/"+ DIRECTORY_BASED_PROJECT_DIR +"/";

  public static boolean isProjectOrWorkspaceFile(final VirtualFile file) {
    return isProjectOrWorkspaceFile(file, file.getFileType());
  }

  public static boolean isProjectOrWorkspaceFile(final VirtualFile file,
                                                 final FileType fileType) {
    return file.getPath().contains(PROJECT_DIR_PATTERN);
  }
}
