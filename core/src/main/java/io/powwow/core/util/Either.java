package io.powwow.core.util;

public class Either<Left extends Exception, Right> {
    private final Left left;
    private final Right right;

    public Either(Left left, Right right) {
        this.left = left;
        this.right = right;
    }

    public Left getLeft() {
        return left;
    }

    public Right getRight() {
        return right;
    }

    public boolean isSuccess() {
        return getLeft() == null;
    }

    public static <L extends Exception, R> Either<L, R> left(L left) {
        return new Either<>(left, null);
    }

    public static <L extends Exception, R> Either<L, R> right(R result) {
        return new Either<>(null, result);
    }
}
