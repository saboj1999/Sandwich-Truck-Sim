import java.util.*;

/**
 * A class that generates routes from one point on the city to another
 */
public class RouteMaker {
    private TruckLocation startLocation;
    private ArrayList<NeighborhoodLocation> route;
    private Direction endDirection;
    private int distance;
    private HashMap<NeighborhoodLocation, HashMap<NeighborhoodLocation, Integer>> distanceMap;
    private final boolean leftTurnsAllowed;

    /**
     * Instantiates a new RouteMaker with default offsets
     */
    public RouteMaker(boolean leftTurnsAllowed) {
        this.leftTurnsAllowed = leftTurnsAllowed;
        generateDistanceMap();
    }

    /**
     * Changes the starting location of the routes
     * @param startLocation a TruckLocation of where the routes should start
     */
    public void setStartLocation(TruckLocation startLocation) {
        this.startLocation = startLocation;
    }

    /**
     * Calculates the route from startLocation to a given point
     * and stores the data about the route in the object
     * @param endLocation the destination
     */
    public void calcRoute(Address endLocation) {
        if (isSameBlock(endLocation)) {
            sameBlock(endLocation);
        } else {
            aStar(endLocation);
        }
    }

    /**
     * Returns the route the truck takes
     * @return the route the truck takes
     */
    public ArrayList<NeighborhoodLocation> getRoute() {
        return route;
    }

    /**
     * Gets the direction the truck faces at the end
     * @return the direction the truck faces at the end
     */
    public Direction getEndDirection() {
        return endDirection;
    }

    /**
     * Gets the distance the truck travels
     * @return the distance of the route
     */
    public int getDistance() {
        return distance;
    }

    /**
     * Creates a map of all intersections and their neighbors/distances to neighbors
     */
    private void generateDistanceMap() {
        this.distanceMap = new HashMap<>();
        ArrayList<StreetIntersection> allIntersections = StreetIntersection.getAllIntersections();
        for (StreetIntersection intersection : allIntersections) {
            distanceMap.put(intersection, getNeighbors(intersection));
        }
    }

    /**
     * Adds a location to the route if they are on the same block
     * @param endLocation the destination
     */
    private void sameBlock(Address endLocation) {

        ArrayList<NeighborhoodLocation> path = new ArrayList<>();
        path.add(startLocation.getAddress());
        path.add(endLocation);
        this.route = path;
        this.distance = Math.abs((startLocation.getAddress().getHouseNumber() / 10) - (endLocation.getHouseNumber() / 10));
        this.endDirection = startLocation.getDir();

    }

    /**
     * Calculates the route between two arbitrary addresses
     * @param endLocation the destination
     */
    private void aStar(Address endLocation) {

        //add start to map
        addStartToMap();
        //find intersections connecting to end
        addEndToMap(endLocation);

        //actual a* implementation under here
        TreeMap<Integer, ArrayList<ArrayList<NeighborhoodLocation>>> distances = new TreeMap<>();
        ArrayList<NeighborhoodLocation> firstPath = new ArrayList<>();
        ArrayList<ArrayList<NeighborhoodLocation>> zeroList = new ArrayList<>();
        zeroList.add(firstPath);
        distances.put(0, zeroList);
        NeighborhoodLocation currentNode;

        while (true) {

            ArrayList<ArrayList<NeighborhoodLocation>> closest = distances.firstEntry().getValue();
            int closestDist = distances.firstEntry().getKey();
            ArrayList<NeighborhoodLocation> currentPath = closest.get(0);
            if (currentPath.size() == 0) {
                currentPath.add(startLocation.getAddress());
            }
            currentNode = currentPath.get(currentPath.size() - 1);
            if (currentNode.equals(endLocation)) {
                //end it
                this.route = currentPath;
                this.distance = calcDist(currentPath);
                StreetIntersection lastIntersection = (StreetIntersection) currentPath.get(currentPath.size() - 2);
                setEndDirection(endLocation, lastIntersection);
                break;
            }
            HashMap<NeighborhoodLocation, Integer> neighbors = distanceMap.get(currentNode);
            for (Map.Entry<NeighborhoodLocation, Integer> neighbor : neighbors.entrySet()) {
                int nextDist = calcDist(currentPath) + neighbor.getValue() + getHeuristic(neighbor.getKey(), endLocation);
                //key is nextDist
                if (!distances.containsKey(nextDist)) {
                    distances.put(nextDist, new ArrayList<>());
                }
                ArrayList<NeighborhoodLocation> nextPath = new ArrayList<>(currentPath);
                nextPath.add(neighbor.getKey());
                ArrayList<ArrayList<NeighborhoodLocation>> thesePaths = distances.get(nextDist);
                thesePaths.add(nextPath);
            }
            closest.remove(0);
            if (closest.size() == 0) {
                distances.remove(closestDist);
            }

        }

        distanceMap.remove(startLocation.getAddress());
        removeEndLocation(endLocation);
    }

    /**
     * Takes the endLocation out of the distanceMap
     * @param endLocation the destination
     */
    private void removeEndLocation(Address endLocation) {
        for (StreetIntersection intersection : getEndIntersections(endLocation)) {
            distanceMap.get(intersection).remove(endLocation);
        }
    }

    /**
     * Adds the start location to the distance map
     */
    private void addStartToMap() {
        NeighborhoodLocation firstInt = getClosestIntersection();
        int firstDist;
        if (startLocation.getDir() == Direction.NORTH || startLocation.getDir() == Direction.WEST) {
            firstDist = (startLocation.getAddress().getHouseNumber() % 100) / 10;
        } else {
            firstDist = 10 - ((startLocation.getAddress().getHouseNumber() % 100) / 10);
        }
        HashMap<NeighborhoodLocation, Integer> firstMap = new HashMap<>();
        firstMap.put(firstInt, firstDist);
        distanceMap.put(startLocation.getAddress(), firstMap);
    }

    /**
     * Calculates the final direction the truck is facing
     * @param endLocation the destination
     * @param lastIntersection the last intersection the truck was at
     */
    private void setEndDirection(Address endLocation, StreetIntersection lastIntersection) {
        if (endLocation.getStreet().getDirection() == StreetDirection.EASTWEST) {
            if (lastIntersection.getDirection() == Direction.EAST) {
                this.endDirection = Direction.EAST;
            } else if (lastIntersection.getDirection() == Direction.WEST) {
                this.endDirection = Direction.WEST;
            } else if (lastIntersection.getNsStreet().getNumber() == endLocation.getHouseNumber() / 100) {
                this.endDirection = Direction.EAST;
            } else {
                this.endDirection = Direction.WEST;
            }
        } else {
            if (lastIntersection.getDirection() == Direction.NORTH) {
                this.endDirection = Direction.NORTH;
            } else if (lastIntersection.getDirection() == Direction.SOUTH) {
                this.endDirection = Direction.SOUTH;
            } else if (lastIntersection.getEwStreet().getNumber() == endLocation.getHouseNumber() / 100) {
                this.endDirection = Direction.SOUTH;
            } else {
                this.endDirection = Direction.NORTH;
            }
        }
    }

    /**
     * Checks if the start and end are on the same block and in the right direction
     * @param endLocation the destination
     * @return true if they are in the same block, false if not
     */
    private boolean isSameBlock(Address endLocation) {
        if (startLocation.getAddress().getStreet().equals(endLocation.getStreet())) {
            if (startLocation.getAddress().getHouseNumber() / 100 == endLocation.getHouseNumber() / 100) {
                if (endLocation.getStreet().getDirection() == StreetDirection.EASTWEST) {
                    if (startLocation.getDir() == Direction.EAST) {
                        return endLocation.getHouseNumber() >= startLocation.getAddress().getHouseNumber();
                    } else {
                        return endLocation.getHouseNumber() <= startLocation.getAddress().getHouseNumber();
                    }
                } else {
                    if (startLocation.getDir() == Direction.SOUTH) {
                        return endLocation.getHouseNumber() >= startLocation.getAddress().getHouseNumber();
                    } else {
                        return endLocation.getHouseNumber() <= startLocation.getAddress().getHouseNumber();
                    }
                }
            }
        }
        return false;
    }

    /**
     * Calculates the distance of a given route
     * @param locations the locations along the route
     * @return the distance of the route
     */
    private int calcDist(ArrayList<NeighborhoodLocation> locations) {
        int dist = 0;
        for (int i = 0; i < locations.size() - 1; i++) {
            HashMap<NeighborhoodLocation, Integer> thisDistances = distanceMap.get(locations.get(i));
            dist += thisDistances.get(locations.get(i + 1));
        }
        return dist;
    }

    /**
     * Gets the heuristic for a location
     * @param currentLocation the location of the truck
     * @param endLocation the destination
     * @return the heuristic distance to destination
     */
    private int getHeuristic(NeighborhoodLocation currentLocation, Address endLocation) {
        int blockMultiple = 11;
        if (currentLocation.isAddress()) {
            return 0;
        } else if (!currentLocation.isAddress()) {
            StreetIntersection intersection = (StreetIntersection) currentLocation;
            int val = 0;
            if (endLocation.getStreet().getDirection() == StreetDirection.EASTWEST) {
                Street locEWStreet = intersection.getEwStreet();
                val += Math.abs(locEWStreet.getNumber() - endLocation.getStreet().getNumber()) * blockMultiple;
                int locNSStreetNum = intersection.getNsStreet().getNumber();
                if (locNSStreetNum <= endLocation.getHouseNumber() / 100) {
                    val += Math.abs(locNSStreetNum - (endLocation.getHouseNumber() / 100)) * blockMultiple;
                } else {
                    val += Math.abs(locNSStreetNum - (endLocation.getHouseNumber() / 100) - 1) * blockMultiple;
                }
            } else {
                Street locNSStreet = intersection.getNsStreet();
                val += Math.abs(locNSStreet.getNumber() - endLocation.getStreet().getNumber()) * blockMultiple;
                int locEWStreetNum = intersection.getEwStreet().getNumber();
                if (locEWStreetNum <= endLocation.getHouseNumber() / 100) {
                    val += Math.abs(locEWStreetNum - (endLocation.getHouseNumber() / 100)) * blockMultiple;
                } else {
                    val += Math.abs(locEWStreetNum - (endLocation.getHouseNumber() / 100) - 1) * blockMultiple;
                }
            }
            return val;
        }
        return 0;
    }

    /**
     * Adds all the intersections that connect to the end to distanceMap
     * @param endLocation the destination
     */
    private void addEndToMap(Address endLocation) {
        if (endLocation.getStreet().getDirection() == StreetDirection.EASTWEST) {
            Street horizontalStreet = endLocation.getStreet();
            int distFromWest = (endLocation.getHouseNumber() % 100) / 10;
            int distFromEast = 10 - distFromWest;
            int vertStreetNum = endLocation.getHouseNumber() / 100;
            Street vertStreet1 = new Street((char)(vertStreetNum + 64) + " Street", StreetDirection.NORTHSOUTH, vertStreetNum);
            Street vertStreet2 = new Street((char)(vertStreetNum + 65) + " Street", StreetDirection.NORTHSOUTH, vertStreetNum + 1);
            StreetIntersection intersection1 = new StreetIntersection(horizontalStreet, vertStreet1, Direction.NORTH);
            StreetIntersection intersection2 = new StreetIntersection(horizontalStreet, vertStreet1, Direction.EAST);
            StreetIntersection intersection3 = new StreetIntersection(horizontalStreet, vertStreet2, Direction.SOUTH);
            StreetIntersection intersection4 = new StreetIntersection(horizontalStreet, vertStreet2, Direction.WEST);
            distanceMap.get(intersection1).put(endLocation, distFromWest);
            distanceMap.get(intersection2).put(endLocation, distFromWest);
            distanceMap.get(intersection3).put(endLocation, distFromEast);
            distanceMap.get(intersection4).put(endLocation, distFromEast);
            if (leftTurnsAllowed) {
                StreetIntersection intersection5 = new StreetIntersection(horizontalStreet, vertStreet1, Direction.SOUTH);
                StreetIntersection intersection6 = new StreetIntersection(horizontalStreet, vertStreet2, Direction.NORTH);
                distanceMap.get(intersection5).put(endLocation, distFromWest);
                distanceMap.get(intersection6).put(endLocation, distFromEast);
            }


        } else {

            Street vertStreet = endLocation.getStreet();
            int distFromNorth = (endLocation.getHouseNumber() % 100) / 10;
            int distFromSouth = 10 - distFromNorth;
            int horizontalStreetNum = endLocation.getHouseNumber() / 100;
            Street horizontalStreet1 = new Street((horizontalStreetNum + 1) + " Street", StreetDirection.EASTWEST, horizontalStreetNum + 1);
            Street horizontalStreet2 = new Street(horizontalStreetNum + " Street", StreetDirection.EASTWEST, horizontalStreetNum);
            StreetIntersection intersection1 = new StreetIntersection(horizontalStreet1, vertStreet, Direction.NORTH);
            StreetIntersection intersection2 = new StreetIntersection(horizontalStreet1, vertStreet, Direction.WEST);
            StreetIntersection intersection3 = new StreetIntersection(horizontalStreet2, vertStreet, Direction.SOUTH);
            StreetIntersection intersection4 = new StreetIntersection(horizontalStreet2, vertStreet, Direction.EAST);
            distanceMap.get(intersection1).put(endLocation, distFromSouth);
            distanceMap.get(intersection2).put(endLocation, distFromSouth);
            distanceMap.get(intersection3).put(endLocation, distFromNorth);
            distanceMap.get(intersection4).put(endLocation, distFromNorth);
            if (leftTurnsAllowed) {
                StreetIntersection intersection5 = new StreetIntersection(horizontalStreet1, vertStreet, Direction.EAST);
                StreetIntersection intersection6 = new StreetIntersection(horizontalStreet2, vertStreet, Direction.WEST);
                distanceMap.get(intersection5).put(endLocation, distFromSouth);
                distanceMap.get(intersection6).put(endLocation, distFromNorth);
            }

        }
    }

    /**
     * Gets all the intersections that connect to the destination
     * @param endLocation the destination
     * @return a list of StreetIntersections that connect to destination
     */
    private ArrayList<StreetIntersection> getEndIntersections(Address endLocation) {
        ArrayList<StreetIntersection> endIntersections = new ArrayList<>();
        if (endLocation.getStreet().getDirection() == StreetDirection.EASTWEST) {
            Street horizontalStreet = endLocation.getStreet();
            int vertStreetNum = endLocation.getHouseNumber() / 100;
            Street vertStreet1 = new Street((char)(vertStreetNum + 64) + " Street", StreetDirection.NORTHSOUTH, vertStreetNum);
            Street vertStreet2 = new Street((char)(vertStreetNum + 65) + " Street", StreetDirection.NORTHSOUTH, vertStreetNum + 1);
            StreetIntersection intersection1 = new StreetIntersection(horizontalStreet, vertStreet1, Direction.NORTH);
            StreetIntersection intersection2 = new StreetIntersection(horizontalStreet, vertStreet1, Direction.EAST);
            StreetIntersection intersection3 = new StreetIntersection(horizontalStreet, vertStreet2, Direction.SOUTH);
            StreetIntersection intersection4 = new StreetIntersection(horizontalStreet, vertStreet2, Direction.WEST);
            endIntersections.add(intersection1);
            endIntersections.add(intersection2);
            endIntersections.add(intersection3);
            endIntersections.add(intersection4);
            if (leftTurnsAllowed) {
                StreetIntersection intersection5 = new StreetIntersection(horizontalStreet, vertStreet1, Direction.SOUTH);
                StreetIntersection intersection6 = new StreetIntersection(horizontalStreet, vertStreet2, Direction.NORTH);
                endIntersections.add(intersection5);
                endIntersections.add(intersection6);
            }


        } else {

            Street vertStreet = endLocation.getStreet();
            int horizontalStreetNum = endLocation.getHouseNumber() / 100;
            Street horizontalStreet1 = new Street((horizontalStreetNum + 1) + " Street", StreetDirection.EASTWEST, horizontalStreetNum + 1);
            Street horizontalStreet2 = new Street(horizontalStreetNum + " Street", StreetDirection.EASTWEST, horizontalStreetNum);
            StreetIntersection intersection1 = new StreetIntersection(horizontalStreet1, vertStreet, Direction.NORTH);
            StreetIntersection intersection2 = new StreetIntersection(horizontalStreet1, vertStreet, Direction.WEST);
            StreetIntersection intersection3 = new StreetIntersection(horizontalStreet2, vertStreet, Direction.SOUTH);
            StreetIntersection intersection4 = new StreetIntersection(horizontalStreet2, vertStreet, Direction.EAST);
            endIntersections.add(intersection1);
            endIntersections.add(intersection2);
            endIntersections.add(intersection3);
            endIntersections.add(intersection4);
            if (leftTurnsAllowed) {
                StreetIntersection intersection5 = new StreetIntersection(horizontalStreet1, vertStreet, Direction.EAST);
                StreetIntersection intersection6 = new StreetIntersection(horizontalStreet2, vertStreet, Direction.WEST);
                endIntersections.add(intersection5);
                endIntersections.add(intersection6);
            }

        }
        return endIntersections;
    }

    /**
     * Finds the closest intersection to the start location
     * @return the closest StreetIntersection to the start location
     */
    private NeighborhoodLocation getClosestIntersection() {
        Direction startDir = startLocation.getDir();
        int vertStreetNum;
        int horizStreetNum;
        Street vertStreet;
        Street horizStreet;
        if (startDir == Direction.NORTH) {
            vertStreet = startLocation.getAddress().getStreet();
            horizStreetNum = startLocation.getAddress().getHouseNumber() / 100;
            horizStreet = new Street(horizStreetNum + " Street", StreetDirection.EASTWEST, horizStreetNum);
        } else if (startDir == Direction.SOUTH) {
            vertStreet = startLocation.getAddress().getStreet();
            horizStreetNum = (startLocation.getAddress().getHouseNumber() / 100) + 1;
            horizStreet = new Street(horizStreetNum + " Street", StreetDirection.EASTWEST, horizStreetNum);
        } else if (startDir == Direction.EAST) {
            horizStreet = startLocation.getAddress().getStreet();
            vertStreetNum = (startLocation.getAddress().getHouseNumber() / 100) + 1;
            vertStreet = new Street((char)(vertStreetNum + 64) + " Street", StreetDirection.NORTHSOUTH, vertStreetNum);
        } else {
            horizStreet = startLocation.getAddress().getStreet();
            vertStreetNum = startLocation.getAddress().getHouseNumber() / 100;
            vertStreet = new Street((char) (vertStreetNum + 64) + " Street", StreetDirection.NORTHSOUTH, vertStreetNum);
        }

        return new StreetIntersection(horizStreet, vertStreet, startDir);

    }

    /**
     * Gets the neighboring intersections to given intersection
     * @param intersection the current intersection
     * @return a list of the intersections that border the current one
     */
    private HashMap<NeighborhoodLocation, Integer> getNeighbors(StreetIntersection intersection) {
        HashMap<NeighborhoodLocation, Integer> neighbors = new HashMap<>();
        Street vertStreet = intersection.getNsStreet();
        Street horizStreet = intersection.getEwStreet();
        if (intersection.getDirection() == Direction.NORTH) {
            NeighborhoodLocation edge1 = turnNorth(intersection);
            if (edge1 != null)
                neighbors.put(edge1, 10);
            NeighborhoodLocation edge2 = turnEast(intersection);
            if (edge2 != null)
                neighbors.put(edge2, 10);
        } else if (intersection.getDirection() == Direction.EAST) {
            NeighborhoodLocation edge1 = turnEast(intersection);
            if (edge1 != null)
                neighbors.put(edge1, 10);
            NeighborhoodLocation edge2 = turnSouth(intersection);
            if (edge2 != null)
                neighbors.put(edge2, 10);
        } else if (intersection.getDirection() == Direction.SOUTH) {
            NeighborhoodLocation edge1 = turnSouth(intersection);
            if (edge1 != null)
                neighbors.put(edge1, 10);
            NeighborhoodLocation edge2 = turnWest(intersection);
            if (edge2 != null)
                neighbors.put(edge2, 10);
        } else {
            NeighborhoodLocation edge1 = turnWest(intersection);
            if (edge1 != null)
                neighbors.put(edge1, 10);
            NeighborhoodLocation edge2 = turnNorth(intersection);
            if (edge2 != null)
                neighbors.put(edge2, 10);
        }
        if (leftTurnsAllowed) {
            if (intersection.getDirection() == Direction.NORTH) {
                NeighborhoodLocation edge3 = turnWest(intersection);
                if (edge3 != null)
                    neighbors.put(edge3, 10);
            } else if (intersection.getDirection() == Direction.EAST) {
                NeighborhoodLocation edge3 = turnNorth(intersection);
                if (edge3 != null)
                    neighbors.put(edge3, 10);
            } else if (intersection.getDirection() == Direction.SOUTH) {
                NeighborhoodLocation edge3 = turnEast(intersection);
                if (edge3 != null)
                    neighbors.put(edge3, 10);
            } else {
                NeighborhoodLocation edge3 = turnSouth(intersection);
                if (edge3 != null)
                    neighbors.put(edge3, 10);
            }
        }
        return neighbors;
    }

    /**
     * Calculates the intersection to the north
     * @param intersection the starting intersection
     * @return the intersection to the north
     */
    private NeighborhoodLocation turnNorth(StreetIntersection intersection) {
        Street horizStreet = intersection.getEwStreet();
        Street vertStreet = intersection.getNsStreet();
        int newHorizontalStreetNum = horizStreet.getNumber() - 1;
        if (newHorizontalStreetNum > 0) {
            Street newHorizStreet = new Street(newHorizontalStreetNum + " Street", StreetDirection.EASTWEST, newHorizontalStreetNum);
            return new StreetIntersection(newHorizStreet, vertStreet, Direction.NORTH);
        } else {
            return null;
        }
    }

    /**
     * Calculates the intersection to the east
     * @param intersection the starting intersection
     * @return the intersection to the east
     */
    private NeighborhoodLocation turnEast(StreetIntersection intersection) {
        Street horizStreet = intersection.getEwStreet();
        Street vertStreet = intersection.getNsStreet();
        int newVertStreetNum = vertStreet.getNumber() + 1;
        if (newVertStreetNum <= 10) {
            Street newVertStreet = new Street((char) (newVertStreetNum + 64) + " Street", StreetDirection.NORTHSOUTH, newVertStreetNum);
            return new StreetIntersection(horizStreet, newVertStreet, Direction.EAST);
        } else {
            return null;
        }
    }

    /**
     * Calculates the intersection to the south
     * @param intersection the starting intersection
     * @return the intersection to the south
     */
    private NeighborhoodLocation turnSouth(StreetIntersection intersection) {
        Street horizStreet = intersection.getEwStreet();
        Street vertStreet = intersection.getNsStreet();
        int newHorizStreetNum = horizStreet.getNumber() + 1;
        if (newHorizStreetNum <= 10) {
            Street newHorizStreet = new Street(newHorizStreetNum + " Street", StreetDirection.EASTWEST, newHorizStreetNum);
            return new StreetIntersection(newHorizStreet, vertStreet, Direction.SOUTH);

        } else {
            return null;
        }

    }

    /**
     * Calculates the intersection to the west
     * @param intersection the starting intersection
     * @return the intersection to the west
     */
    private NeighborhoodLocation turnWest(StreetIntersection intersection) {
        Street horizStreet = intersection.getEwStreet();
        Street vertStreet = intersection.getNsStreet();
        int newVertStreetNum = vertStreet.getNumber() - 1;
        if (newVertStreetNum > 0) {
            Street newVertStreet = new Street((char)(newVertStreetNum + 64) + " Street", StreetDirection.NORTHSOUTH, newVertStreetNum);
            return new StreetIntersection(horizStreet, newVertStreet, Direction.WEST);
        } else {
            return null;
        }
    }

}
