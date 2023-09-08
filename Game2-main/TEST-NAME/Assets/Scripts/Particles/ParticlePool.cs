using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.Pool;
public abstract class ParticlePool : MonoBehaviour
{
    private Pool particleSpawner;
    private GameObject _particleSystem;
    private string particleSpawnerTag;
    // Start is called before the first frame update
    void Start()
    {
        SetPool();
        SetStopActionToCallback();
    }

    private void OnParticleSystemStopped() 
    {
        particleSpawner.ReturnObject(this.gameObject);
    }

    public void SetParticleSapwnerTag(string tag)
    {
        particleSpawnerTag = tag;
    }

    protected abstract void SetParticleSpawner();

    private void SetPool()
    {
        SetParticleSpawner();
        particleSpawner = GameObject.FindWithTag(particleSpawnerTag).GetComponent<Pool>();
    }

    private void SetStopActionToCallback()
    {
        ParticleSystem.MainModule main = GetComponent<ParticleSystem>().main;
        main.stopAction = ParticleSystemStopAction.Callback;
    }
}