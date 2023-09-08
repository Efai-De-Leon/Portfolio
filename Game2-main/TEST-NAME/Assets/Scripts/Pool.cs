using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.Pool;

public class Pool : MonoBehaviour
{
    [SerializeField] GameObject prefab;
    [SerializeField] int poolSize;
    [SerializeField] int maxPoolSize;
    private ObjectPool<GameObject> pool;

    void Start()
    {
        pool = new ObjectPool<GameObject>(() => {return Instantiate(prefab, this.transform);},
            objectInstance => {objectInstance.gameObject.SetActive(true);},
            objectInstance => {objectInstance.gameObject.SetActive(false);},
            objectInstance => {Destroy(objectInstance);},
            false, poolSize, maxPoolSize);
    }

    public GameObject GetObject()
    {
        return pool.Get();
    }

    public void ReturnObject(GameObject objectToReturn)
    {
        pool.Release(objectToReturn);
    }
}
