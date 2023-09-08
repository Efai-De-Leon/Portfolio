using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.AI;
using UnityEngine.VFX;
public class NeedleEnemy : MonoBehaviour
{
    private Animator needleEnemyAnimator;
    private GameManager gameManager;
    private EnemyNeedleMovementAI needleEnemyMovementAI;
    private NavMeshAgent needleEnemyNavMeshAgent;
    private Pool enemySpawner;
    private Pool VFXSpawner;
    private VisualEffect needleEnemyDeathVFX;
    private int health = 5;
    private Material material;
    // Start is called before the first frame update
    void Start()
    {
        gameManager = GameObject.Find("GameManager").GetComponent<GameManager>();
        needleEnemyNavMeshAgent = GetComponent<NavMeshAgent>();
        enemySpawner = GameObject.FindWithTag("NeedleEnemySpawner").GetComponent<Pool>();
        VFXSpawner = GameObject.FindWithTag("VFXSpawner").GetComponent<Pool>();
        needleEnemyAnimator = GetComponent<Animator>();
        material = GetComponentInChildren<Renderer>().material;
        needleEnemyMovementAI = GetComponent<EnemyNeedleMovementAI>();
    }

    // Update is called once per frame
    void Update()
    {
        if (!gameManager.IsGameActive())
        {
            needleEnemyMovementAI.SetStop(true);
        }
    }

    void OnCollisionEnter(Collision collision)
    {
        if (collision.gameObject.CompareTag("Player"))
        {
        }
    }

    public void ResetHealth()
    {
        health = 5;
    }

    public void KillEnemy()
    {
        health--;
        material.color = Color.Lerp(Color.red, Color.white, health / 5f);    
        Debug.Log(health);
        if (health <= 0)
        {
            Die();
        }
    }

    void Die()
    {
        needleEnemyDeathVFX = VFXSpawner.GetObject().GetComponent<VisualEffect>();
        needleEnemyDeathVFX.transform.position = transform.position;
        needleEnemyDeathVFX.Play();
        gameManager.UpdateScore(10);
        gameManager.UpdateNumOfEnemiesOnScreen(-1);
        gameObject.SetActive(false);
        material.color = Color.white;
        needleEnemyNavMeshAgent.enabled = false;
        enemySpawner.ReturnObject(gameObject);
    } 
}
