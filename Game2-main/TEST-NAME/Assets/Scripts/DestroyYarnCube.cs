using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class DestroyYarnCube : MonoBehaviour
{
    [SerializeField] ParticleSystem yarnCubeExplosion;
    [SerializeField] EnemyNeedleMovementAI patrolAgent;
    private Pool particleSpawner;
    void Start()
    {
        //may need to fix
        particleSpawner = GameObject.FindWithTag("CottonParticleSpawner").GetComponent<Pool>();
    }

    void OnCollisionEnter(Collision collision)
    {
        if (collision.gameObject.CompareTag("Player"))
        {
            ParticleSystem particle = particleSpawner.GetObject().GetComponent<ParticleSystem>();
            particle.transform.position = transform.position;
            particle.Play();
            //gameObject should be set inactive instead of destroyed
            gameObject.SetActive(false);
            //patrolAgent.UnblockWaypoint(transform.position);
        }
    }
}
