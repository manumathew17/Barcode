package com.manu.scanner.ee.barcodescanner.camera;


import com.manu.scanner.ee.barcodescanner.SourceData;

/**
 * Callback for camera previews.
 */
public interface PreviewCallback {
    void onPreview(SourceData sourceData);
    void onPreviewError(Exception e);
}
