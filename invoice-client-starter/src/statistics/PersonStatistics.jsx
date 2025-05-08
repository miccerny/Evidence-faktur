import React, { useState, useEffect } from "react";
import { apiGet } from "../utils/api";

const PersonStatistics = () => {
    const [personStats, setPersonStats] = useState([]);

    useEffect(() => {
        apiGet("/api/persons/statistics")
            .then(data => {
                console.log("Statistiky osob:", data);
                setPersonStats(data);
            })
            .catch(error => console.error("chyba při načítání statistik:", error));

    }, []);

    return (
        <div>
        <h1 className="d-flex justify-content-center"> Statistiky osob</h1>
        <div className="container">
            <div className="row g-3">
                {personStats.map((stat) => (
                    <div className="col-12 col-md-4" key={stat.personId}>
                        <div className="card shadow-sm h-100">
                        <div className="card-body position-relative">
                        <div className="d-flex justify-content-between align-items-center gap-3 m-1">
                    <h5 className="card-title mb-0">{stat.personName}</h5>
                    <p className="text mb-0"><strong>Příjmy: </strong>{stat.revenue} Kč</p>
                    </div>
                    </div>
                  </div>
                  </div>


                ))}
            </div>
        </div>
        </div>

    );
}
export default PersonStatistics;