package org.zyneonstudios.apex.utilities.json;

import org.zyneonstudios.apex.utilities.storage.EditableStorage;

public interface EditableJsonData extends JsonData, EditableStorage {

    void clear();
}
