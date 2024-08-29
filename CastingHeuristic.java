import java.util.*;

// Code written by @sannerv

public class CastingHeuristic {
    static Kattio io;
    public static int numRoles;
    public static int numScenes;
    public static int numActors;
    public static HashMap<Integer, TreeSet<Integer>> roleToActors = new HashMap<>();
    public static HashMap<Integer, List<Integer>> sceneToRoles = new HashMap<>();
    public static HashMap<Integer, Set<Integer>> divasToRoles = new HashMap<>();
    public static HashMap<Integer, Set<Integer>> actorsToRoles = new HashMap<>();
    public static HashMap<Integer, Set<Integer>> roleGraph = new HashMap<>();


    public static void main(String[] args) {
        io = new Kattio(System.in, System.out);

        // Read in number of roles, scenes, and actors
        numRoles = io.getInt();
        numScenes = io.getInt();
        numActors = io.getInt();

        // Read in what actors can play each role
        for (int i = 1; i <= numRoles; i++) {
            int numActorsForRole = io.getInt();
            TreeSet<Integer> actors = new TreeSet<>();
            for (int j = 0; j < numActorsForRole; j++) {
                actors.add(io.getInt());
            }
            roleToActors.put(i, actors);
        }

        // Read in roles for each scene
        for (int i = 1; i <= numScenes; i++) {
            int numRolesInScene = io.getInt();
            List<Integer> roles = new ArrayList<>();
            for (int j = 0; j < numRolesInScene; j++) {
                roles.add(io.getInt());
            }
            sceneToRoles.put(i, roles);
        }

        // Create role graph
        for (int scene : sceneToRoles.keySet()) {
            List<Integer> roles = sceneToRoles.get(scene);
            for (int i = 0; i < roles.size(); i++) {
                for (int j = i + 1; j < roles.size(); j++) {
                    int role1 = roles.get(i);
                    int role2 = roles.get(j);
                    if (!roleGraph.containsKey(role1)) {
                        roleGraph.put(role1, new HashSet<>());
                    }
                    if (!roleGraph.containsKey(role2)) {
                        roleGraph.put(role2, new HashSet<>());
                    }
                    roleGraph.get(role1).add(role2);
                    roleGraph.get(role2).add(role1);
                }
            }
        }

        assignDivas();
        assignActorsToRoles();
        printSolution();
    }

    // Start by assigning divas
    public static void assignDivas() {
        // Save all roles that the two divas can play
        for (int role : roleToActors.keySet()) {
            if (roleToActors.get(role).contains(1)) {
                if (!divasToRoles.containsKey(1)) {
                    divasToRoles.put(1, new HashSet<>());
                }
                divasToRoles.get(1).add(role);
            }
            if (roleToActors.get(role).contains(2)) {
                if (!divasToRoles.containsKey(2)) {
                    divasToRoles.put(2, new HashSet<>());
                }
                divasToRoles.get(2).add(role);
            }
        }

        main:
        for (int role1 : divasToRoles.get(1)) {
            for (int role2 : divasToRoles.get(2)) {
                // Check that the roles do not play in the same scene, and that the scenes are not the same.
                if (!roleGraph.get(role1).contains(role2) && role1 != role2) {
                    if (!actorsToRoles.containsKey(1)) {
                        actorsToRoles.put(1, new HashSet<>());
                    }
                    actorsToRoles.get(1).add(role1);
                    if (!actorsToRoles.containsKey(2)) {
                        actorsToRoles.put(2, new HashSet<>());
                    }
                    actorsToRoles.get(2).add(role2);
                    //Both divas have been assigned a role, break loop.
                    break main;
                }
            }
        }
    }

    // Assign the rest of the actors
    public static void assignActorsToRoles() {
        Map<Integer, Integer> divaRoles = new HashMap<>();
        for (int diva : actorsToRoles.keySet()) {
            for (int role : actorsToRoles.get(diva)) {
                divaRoles.put(diva, role);
            }
        }

        // Make a new roleToActors, without the Divas
        Map<Integer, TreeSet<Integer>> rolesToAssign = new HashMap<>();
        for (int role : roleToActors.keySet()) {
            if (!divaRoles.containsValue(role)) {
                rolesToAssign.put(role, roleToActors.get(role));
            }
        }

        // Remove all occurrences of actors 1 and 2 from the hashmap
        for (Map.Entry<Integer, TreeSet<Integer>> entry : rolesToAssign.entrySet()) {
            TreeSet<Integer> actors = entry.getValue();
            actors.remove(1);
            actors.remove(2);
            if(actors.isEmpty()) {
                actors.add(0);
            }
        }

        // Assign the rest of the actors
        int superActor = numActors + 1;
        for (int role : rolesToAssign.keySet()) {
            for (int actor : rolesToAssign.get(role)) {
                if (actor != 0) {
                    if (actorsToRoles.get(actor) == null) {
                        actorsToRoles.put(actor, new HashSet<>());
                        actorsToRoles.get(actor).add(role);
                        break;
                    }
                    boolean canAssign = true;
                    // Check if any of the other roles the actor has been assigned
                    // plays in the same scene as the current role we are trying to assing
                    for (int assignedRole : actorsToRoles.get(actor)) {
                        //if it do we can't assign the actor that role
                        if (roleGraph.get(role).contains(assignedRole)) {
                            canAssign = false;
                            break;
                        }
                    }
                    if (canAssign) {
                        actorsToRoles.get(actor).add(role);
                        break;
                    }
                }
                // All actors that can play the role has not been able to receive the role
                // Therefore add super actor.
                if (actor == rolesToAssign.get(role).last()) {
                    actorsToRoles.put(superActor, new HashSet<>());
                    actorsToRoles.get(superActor).add(role);
                    superActor++;
                    break;
                }
            }
        }
    }

    public static void printSolution() {
        io.println(actorsToRoles.keySet().size());
        for (int actor : actorsToRoles.keySet()) {
            io.print(actor + " " + actorsToRoles.get(actor).size() + " ");
            for (int role : actorsToRoles.get(actor)) {
                io.print(role + " ");
            }
            io.println();
        }
        io.flush();
        io.close();
    }
}