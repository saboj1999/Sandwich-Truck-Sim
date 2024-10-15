public interface ObserverSubject {
    void registerObserver(TruckObserver observer);
    void removeObserver(TruckObserver observer);
    void notifyObservers(Coordinate coordinate);
}
