using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class SpawnEnemiesManager : MonoBehaviour
{
    [SerializeField] GameManager gameManager;
    public int numOfNeedleEnemiesToSpawn = 3;
    private int needleEnemyStartX = -35;
    private int needleEnemyStartY = 26;
    private int needleEnemyStartZ = 1;
    private Pool pool;

    // Start is called before the first frame update
    void Start()
    {
        pool = GetComponent<Pool>();
        StartCoroutine(SpawnNeedleEnemiesCoroutine(numOfNeedleEnemiesToSpawn));

    }

    // Update is called once per frame
    void Update(){}

    public void SpawnNeedleEnemy()
    {
        GameObject enemy = pool.GetObject();
        enemy.GetComponent<NeedleEnemy>().ResetHealth(); 
        enemy.transform.position = new Vector3(needleEnemyStartX, needleEnemyStartY, needleEnemyStartZ);
        gameManager.UpdateNumOfEnemiesOnScreen(1); 
    }

    IEnumerator SpawnNeedleEnemiesCoroutine(int numOfEnemies)
    {
        while (true)
        {
            yield return new WaitForSeconds(3.0f);
            if (gameManager.GetNumOfEnemiesOnScreen() < numOfEnemies)
                SpawnNeedleEnemy();
        }
    }
}
