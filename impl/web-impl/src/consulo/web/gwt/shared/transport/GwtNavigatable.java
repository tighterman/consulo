/*
 * Copyright 2013-2016 consulo.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package consulo.web.gwt.shared.transport;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author VISTALL
 * @since 19-May-16
 */
public class GwtNavigatable implements IsSerializable {
  private GwtVirtualFile myFile;
  private int myOffset;

  public GwtNavigatable(GwtVirtualFile file, int offset) {
    myFile = file;
    myOffset = offset;
  }

  public GwtNavigatable() {
  }

  public GwtVirtualFile getFile() {
    return myFile;
  }

  public int getOffset() {
    return myOffset;
  }
}
