package com.slogup.sgcore.network;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by sngjoong on 16. 8. 25..
 */
public class MultipartRequest extends Request<NetworkResponse> {

    private final String TWO_HYPHENS = "--";
    private final String LINE_END = "\r\n";
    private final String BOUNDARY = "apiclient-" + System.currentTimeMillis();

    private Response.Listener<NetworkResponse> mListener;
    private Response.ErrorListener mErrorListener;


    public MultipartRequest(int method, String url, Response.Listener<NetworkResponse> listener, Response.ErrorListener errorListener) {

        super(method, url, errorListener);
        mListener = listener;
        mErrorListener = errorListener;
    }


    @Override
    public byte[] getBody() throws AuthFailureError {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        try {

            Map<String, String> params = getParams();

            if (params != null && !params.isEmpty())
                textParse(dos, params, getParamsEncoding());

            Map<String, DataPart> data = getByteData();
            if (data != null && !data.isEmpty())
                dataParse(dos, data);

            dos.writeBytes(TWO_HYPHENS + BOUNDARY + TWO_HYPHENS + LINE_END);

            return bos.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    protected Map<String, DataPart> getByteData() throws  AuthFailureError {

        return null;
    }

    @Override
    protected Response<NetworkResponse> parseNetworkResponse(NetworkResponse response) {

        try {

            return Response.success(response, HttpHeaderParser.parseCacheHeaders(response));

        } catch (Exception e) {

            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(NetworkResponse response) {

        mListener.onResponse(response);
    }

    @Override
    public void deliverError(VolleyError error) {

        mErrorListener.onErrorResponse(error);
    }

    private void textParse(DataOutputStream dataOutputStream, Map<String, String> params, String encoding) throws IOException {

        try {

            for (Map.Entry<String, String> entry : params.entrySet()) {

                buildTextPart(dataOutputStream, entry.getKey(), entry.getValue());
            }
        } catch (UnsupportedEncodingException e) {

            throw new RuntimeException("Encoding not supported " + encoding, e);
        }
    }

    private void dataParse(DataOutputStream dataOutputStream, Map<String, DataPart> data) throws IOException {

        for (Map.Entry<String, DataPart> entry : data.entrySet()) {

            buildDataPart(dataOutputStream, entry.getValue(), entry.getKey());
        }
    }

    private void buildTextPart(DataOutputStream dataOutputStream, String key, String value) throws IOException {

        dataOutputStream.writeBytes(TWO_HYPHENS + BOUNDARY + LINE_END);
        dataOutputStream.writeBytes("Content-disposition: form-data; name=\"" + key + "\"" + LINE_END);
        dataOutputStream.writeBytes(LINE_END);
        dataOutputStream.writeBytes(value + LINE_END);
    }

    private void buildDataPart(DataOutputStream dataOutputStream, DataPart dataFile, String inputName) throws IOException {

        dataOutputStream.writeBytes(TWO_HYPHENS + BOUNDARY + LINE_END);
        dataOutputStream.writeBytes("Content-disposition: form-data; name=\"" + inputName + "\"; filename=\"" + dataFile.getFileName() + "\"" + LINE_END);

        if (dataFile.getType() != null && !dataFile.getType().trim().isEmpty()) {

            dataOutputStream.writeBytes("Content-Type: " + dataFile.getType() + LINE_END);
        }

        dataOutputStream.writeBytes(LINE_END);

        ByteArrayInputStream fileInputStream = new ByteArrayInputStream(dataFile.getContent());
        int bytesAvailable = fileInputStream.available();

        int maxBufferSize = 1024 * 1024;
        int bufferSize = Math.min(bytesAvailable, maxBufferSize);
        byte[] buffer = new byte[bufferSize];

        int bytesRead = fileInputStream.read(buffer, 0, bufferSize);

        while (bytesRead > 0) {

            dataOutputStream.write(buffer, 0, bufferSize);
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
        }

        dataOutputStream.writeBytes(LINE_END);
    }


    public class DataPart {

        private String fileName;
        private byte[] content;
        private String type;


        public DataPart() {
        }

        public DataPart(String fileName, byte[] content) {
            this.fileName = fileName;
            this.content = content;
        }

        public DataPart(String fileName, byte[] content, String mimeType) {
            this.fileName = fileName;
            this.content = content;
            this.type = mimeType;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public byte[] getContent() {
            return content;
        }

        public void setContent(byte[] content) {
            this.content = content;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

}
