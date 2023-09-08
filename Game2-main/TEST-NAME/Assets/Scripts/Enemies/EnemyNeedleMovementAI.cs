using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.AI;

public class EnemyNeedleMovementAI : MonoBehaviour
{
    private int gridWidth = 40;
    private int gridHeight = 27;
    public float gridSpacing = 2f;
    public LayerMask obstacleMask;

    private Dictionary<Vector3, bool> waypointDict; // Changed to Dictionary
    private Vector3 currentDestination;

    [SerializeField] NavMeshAgent agent;
    bool created = false;
    bool stop = false;

    void OnEnable()
    {
        if (created)
        {
            agent.transform.position = new Vector3(-35, 26, 1);
            agent.enabled = true;
            stop = false;
        }
    }

    private void Start()
    {
        created = true;
        agent.enabled = true;
        if (agent == null)
        {
            Debug.LogError("No NavMeshAgent component found on this game object.");
            return;
        }

        waypointDict = new Dictionary<Vector3, bool>(); // Initialize Dictionary

        // Generate waypoints
        for (int x = 0; x < gridWidth; x++)
        {
            for (int y = 0; y < gridHeight; y++)
            {
                float xPos = x * gridSpacing;
                float yPos = y * gridSpacing;
                Vector3 waypoint = new Vector3(-xPos, yPos, 0);

                // Add all waypoints to the dictionary, mark blocked ones
                if (Physics.CheckSphere(waypoint, 1f, obstacleMask))
                {
                    waypointDict[waypoint] = false; // Waypoint blocked
                }
                else
                {
                    waypointDict[waypoint] = true; // Waypoint free
                }
            }
        }

        // Set initial destination
        SetRandomDestination();
    }

    private void Update()
    {
        if (!agent.pathPending && agent.remainingDistance <= 2f && agent.enabled == true)
        {
            SetRandomDestination();
        }
        ControlAgentStop();
    }

    private void SetRandomDestination()
    {
        List<Vector3> freeWaypoints = new List<Vector3>();

        foreach (KeyValuePair<Vector3, bool> waypoint in waypointDict)
        {
            if (waypoint.Value == true) // If the waypoint is free
            {
                freeWaypoints.Add(waypoint.Key);
            }
        }

        if (freeWaypoints.Count > 0)
        {
            Vector3 randomDestination = freeWaypoints[Random.Range(0, freeWaypoints.Count)];
            agent.SetDestination(randomDestination);
            currentDestination = randomDestination;
        }
    }

    public void UnblockWaypoint(Vector3 position, float tolerance = 1f)
    {
        Vector3 closestWaypoint = Vector3.positiveInfinity;
        float closestDistance = Mathf.Infinity;

        foreach (Vector3 waypoint in waypointDict.Keys)
        {
            float distance = Vector3.Distance(position, waypoint);

            if (distance < closestDistance)
            {
                closestDistance = distance;
                closestWaypoint = waypoint;
            }
        }

        if (closestDistance <= tolerance)
        {
            waypointDict[closestWaypoint] = true; // Unblock waypoint
        }
    }

    void OnDrawGizmosSelected()
    {
        if (waypointDict != null)
        {
            foreach (KeyValuePair<Vector3, bool> waypoint in waypointDict)
            {
                Gizmos.color = waypoint.Value ? Color.red : Color.gray; // Blocked waypoints will be gray
                if (waypoint.Key == currentDestination)
                {
                    Gizmos.color = Color.blue;
                }
                Gizmos.DrawSphere(waypoint.Key, 0.5f);
            }
        }
    }

    void ControlAgentStop()
    {
        if (stop)
        {
            agent.isStopped = true;
        }
        else
        {
            agent.isStopped = false;
        }
    }

    public void SetStop(bool stop)
    {
        Debug.Log("Stop set to " + stop);
        this.stop = stop;
        StartCoroutine(StopForSeconds(1f));
    }

    IEnumerator StopForSeconds(float seconds)
    {
        yield return new WaitForSeconds(seconds);
        this.stop = false;
    }
}