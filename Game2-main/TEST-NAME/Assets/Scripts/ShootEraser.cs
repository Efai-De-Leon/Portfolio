using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.VFX;
public class ShootEraser : MonoBehaviour
{
    [SerializeField] GameObject eraser;
    [SerializeField] GameObject eraserShootingParticlePrefab;
    private EraserMovement eraserMovement;
    private Vector3 eraserDirection;
    private Rigidbody eraserRb;
    private float speed = 20f;
    private float eraserMaxDistance = 10f;
    private float leftAngle = 90f;
    private float rightAngle = 270f;
    bool isFlying = false;
    Direction eraserDirectionEnum;

    private PlayerMovement playerMovement;
    enum Direction{
        Left,
        Right
    }
    // Start is called before the first frame update
    void Start()
    {
        eraserDirection = new Vector3(-1, 0, 0);
        eraserRb = eraser.GetComponent<Rigidbody>();
        eraserMovement = eraser.GetComponent<EraserMovement>();
        playerMovement = GetComponent<PlayerMovement>();
    }

    void Update()
    {
        if (Input.GetButtonDown("Jump") && !isFlying)
        {
            Fire();
        }
    }
    // Update is called once per frame
    void FixedUpdate()
    {
        if (isFlying)
        {
            eraserRb.MovePosition(eraser.transform.position + eraserDirection * Time.fixedDeltaTime * speed);
        }

        if (IsEraserAtMaxDistance())
        {
            eraserMovement.KillEraser();
            isFlying = false;
        }
    }

    public bool IsFlying()
    {
        return isFlying;
    }

    public void Fire()
    {
        if (playerMovement.GetNumOfEraser() <= 0)
        {
            return;
        }
        playerMovement.UpdateNumOfEraser(playerMovement.GetNumOfEraser() - 1);
        eraserShootingParticlePrefab.GetComponent<VisualEffect>().Play();
        SetEraserDirection();
        SetEraserPosition(1f);
        SetEraserVFXPosition(1f);
        eraserMovement.LaunchEraser();
        isFlying = true;
    }
    void OffsetVFXPosition(float offset)
    {
        Vector3 eraserParticlePosition = transform.position;
        eraserParticlePosition.x += offset;
        eraserShootingParticlePrefab.transform.position = eraserParticlePosition;
    }

    void SetEraserVFXPosition(float offset)
    {
        switch(eraserDirectionEnum)
        {
            case Direction.Left:
                OffsetVFXPosition(-offset);
                break;
            case Direction.Right:
                OffsetVFXPosition(offset);
                break;
        }
    }

    void OffsetEraserPosition(float offset)
    {
        Vector3 eraserPosition = transform.position;
        eraserPosition.x += offset;
        eraser.transform.position = eraserPosition;
    }

    void SetEraserPosition(float offset)
    {
        switch(eraserDirectionEnum)
        {
            case Direction.Left:
                OffsetEraserPosition(-offset);
                break;
            case Direction.Right:
                OffsetEraserPosition(offset);
                break;
        }
    }
    void SetEraserDirection()
    {
        if (transform.rotation.eulerAngles.y ==  leftAngle)
        {
            eraserDirection = Vector3.left;
        }
        else if (transform.rotation.eulerAngles.y == rightAngle)
        {
            eraserDirection = Vector3.right;
        }
    }

    bool IsEraserAtMaxDistance()
    {
        if (Vector3.Distance(eraser.transform.position, transform.position) > eraserMaxDistance)
        {
            return true;
        }
        return false;
    }
}
