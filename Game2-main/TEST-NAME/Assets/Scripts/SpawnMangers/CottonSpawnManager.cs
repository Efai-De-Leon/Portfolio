using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CottonSpawnManager : MonoBehaviour
{
    public GameObject cottonPrefab;
    public GameObject cottonPrefabBlue;
    public GameObject cottonPrefabRed;
    public GameObject cottonPrefabBrown;
    private GameObject[] cottonPrefabs;
    public GameObject leftWall;

    public GameObject rightWall;

    public GameObject topLeftWall;

    public GameObject topRightWall;

    public GameObject bottomWall;

    public float blockWidthSizeOffset = 0.75f;
    private int xRange;
    private int yRange;

    // Start is called before the first frame update
    void Start()
    {
        SpawnCotton();
    }

    // Update is called once per frame
    void Update() { }

    void SpawnCotton()
    {
        // get the max size for the cotton
        xRange = Mathf.Abs(Mathf.RoundToInt(rightWall.transform.position.x - leftWall.transform.position.x)) - 1;
        yRange = Mathf.Abs(Mathf.RoundToInt(topLeftWall.transform.position.y - bottomWall.transform.position.y) - 1);
        // loading the cotton
        float i = 0;
        float j = 0;
        while(i < xRange)
        {
            while(j < yRange)
            {
                GameObject cottonInstance = Instantiate(GetRandomCottonPrefab(), new Vector3(-i, j, 0), cottonPrefab.transform.rotation);
                cottonInstance.transform.parent = transform;
                j+=blockWidthSizeOffset;
            }
            i+=blockWidthSizeOffset;
            j = 0;
        }
    }

    void LoadCottonPrefabs()
    {
        cottonPrefabs = new GameObject[4];
        cottonPrefabs[0] = cottonPrefab;
        cottonPrefabs[1] = cottonPrefabBlue;
        cottonPrefabs[2] = cottonPrefabRed;
        cottonPrefabs[3] = cottonPrefabBrown;
    }

    public GameObject GetRandomCottonPrefab()
    {
        if (cottonPrefabs == null)
            LoadCottonPrefabs();
        return cottonPrefabs[Random.Range(0, cottonPrefabs.Length)];
    }
}
