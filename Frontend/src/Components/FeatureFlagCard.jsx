import { useEffect, useState } from "react";
import "./FeatureFlagCard.css"

function FeatureFlagCard({ flag, setChanged, setUpdateBox, setOverlay, setFlag, setShowFlagId }) {

    const [name, setName] = useState("not found")
    const [description, setDescription] = useState("")
    const [rollout, setRollout] = useState(0)
    const [allowedCountries, setAllowedCountries] = useState([])
    const [lift, setLift] = useState("Unavailable")

    useEffect(() => {
        setName(flag.flagName)
        setDescription(flag.description)
        setRollout(flag.rolloutPercent)
        setAllowedCountries(flag.allowedCountries)

        if (flag.rolloutPercent > 0 && flag.withFlagSuccess > 0 && flag.withoutFlagSuccess) {
            setLift(((flag.withFlagSuccess / flag.withoutFlagSuccess) * ((100 - flag.rolloutPercent) / flag.rolloutPercent)).toFixed(2))
        }
    }, [flag])

    async function deleteFlag(id) {
        try {
            const response = await fetch(`/flags/delete-flag/${id}`, {
                method: "DELETE",
                credentials: "include"
            })

            if (!response.ok) {
                alert("Invalid request")
                return
            }

            alert("Flag deleted")
            setChanged((prev) => !prev)
        } catch (error) {
            alert("Server error!")
        }
    }

    async function resetFlag(id) {
        try {
            const response = await fetch(`/flags/reset-users/${id}`, {
                method: "PUT",
                credentials: "include"
            })

            if (!response.ok) {
                alert("Invalid request")
                return
            }

            alert("Flag users reset")
            setChanged((prev) => !prev)
        } catch (error) {
            alert("Server error!")
        }
    }

    return (
        <>
            <div className="flag-container">
                <div className="name-container">
                    <div className="flag-name">{name}</div>
                    <img src="/edit.png" className="edit-button" onClick={() => {
                        setUpdateBox(true)
                        setOverlay(true)
                        setFlag(flag)
                    }}></img>
                    <img src="/delete.png" className="delete-button" onClick={() => deleteFlag(flag.id)}></img>
                </div>

                <div className="flag-description">
                    {description}
                </div>

                <div className="flag-rollout-percent">
                    <img src="/percentage.png" className="percent-image"></img>
                    Rollout Percent: [{rollout}%]
                </div>

                <div className="flag-allowed-countries">
                    <img src="/world.png" className="world-image"></img>
                    Allowed Countries: [{allowedCountries.length === 0 ?
                        <div>ALL COUNTRIES ALLOWED</div> :
                        <div className="countries">{allowedCountries.join(", ")}</div>
                    }]
                </div>

                <div className="flag-improvement">
                    <img src="/graph.png" className="graph-image"></img>
                    Success Rate Ratio (Feature vs Control): [{lift}]
                </div>

                <div className="flag-buttons">
                    <button className="flag-button-reset" onClick={() => {
                        resetFlag(flag.id)
                    }}>
                        Reset Users
                    </button>
                    <button className="flag-button-id" onClick={() => {
                        setFlag(flag)
                        setOverlay(true)
                        setShowFlagId(true)
                    }}>
                        Show Flag Id
                    </button>
                </div>
            </div>
        </>
    )
}

export default FeatureFlagCard