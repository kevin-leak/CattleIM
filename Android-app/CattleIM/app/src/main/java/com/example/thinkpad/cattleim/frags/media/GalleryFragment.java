package com.example.thinkpad.cattleim.frags.media;

public class GalleryFragment {


    private OnSelectedListener mListener;

    /**
     * 设置事件监听，并返回自己
     *
     * @param listener OnSelectedListener
     * @return GalleryFragment
     */
    public GalleryFragment setListener(OnSelectedListener listener) {
        mListener = listener;
        return this;
    }

    public interface OnSelectedListener {
        void onSelectedImage(String path);
    }
}
