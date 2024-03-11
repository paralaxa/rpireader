package sk.stopangin.rpi;

public interface DataPublisher<T> {

  void publish(T data);
}
