# Heuristic for role assignment problem

This is an implementation of a heuristic that solves a NP-hard problem.

Kattio.java is used for fast I/O

### Problem statement:

The problem is to solve <b>which actors</b> should get <b>which roles</b> with <b>as few actors as possible.</b>

Same person can play multiple roles, but one role can only be assigned to one person.

Every actor can only have one role in each scene, and every role must be part of atleast one scene.

There are also two divas - which are guaranteed to get atleast one role each. Furthermore, the divas can never be in the same scene since they cannot stand eachother.

To make sure the heuristic in polynomial time can be sure to find a solution it is allowed to use a maximum of <b>n-1</b> <i>superactors</i> with numbers <b>k+1</b>, <b>k+2</b>, ... Each superactor can play any role, but only one role.

### Input format:

Three first rows contains integers <b>n</b>, <b>s</b>, and <b>k</b> (number of roles, number of scenes, and number of actors).

The following <b>n</b> rows defines which actors can play a specific role starting with the number of actors which can play that role.

The last <b>s</b> rows defines which roles can play in that scene starting with the number of roles in the scene.

#### Example input:

```
6
5
4
3 1 3 4
2 2 3
2 1 3
1 2
4 1 2 3 4
2 1 4
3 1 2 6
3 2 3 5
3 2 4 6
3 2 3 6
2 1 6
```

6 roles, 5 scenes, 4 actors.

Role 1 can be played by actors 1, 3, and 4

Scene 1 can be played by roles 1, 2, and 6

### Output format:

First row gives the number of actors which has been assigned a role

Followed by a row for each actor (which has been assigned a role) with a the actors number, followed by the number of roles assigned, and the number of the roles

#### Example output:

```
5
1 1 1
2 1 4
3 1 2
4 2 5 6
5 1 3
```

5 actors has been assigned roles

Actor 1 has been assigned 1 role which is role 1

Actor 4 has been assigned 2 roles which is roles 5 and 6
