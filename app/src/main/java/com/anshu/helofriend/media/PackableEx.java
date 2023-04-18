package com.anshu.helofriend.media;

public interface PackableEx extends Packable {
    void unmarshal(ByteBuf in);
}
