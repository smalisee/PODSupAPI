package com.amos.podsupapi.component.aws;

import java.io.IOException;

public interface AWSFileTransferComponent {

	void putObject(String key, byte[] source) throws IOException;

	byte[] getObject(String key) throws IOException;

}
