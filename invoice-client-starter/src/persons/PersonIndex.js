/*  _____ _______         _                      _
 * |_   _|__   __|       | |                    | |
 *   | |    | |_ __   ___| |___      _____  _ __| | __  ___ ____
 *   | |    | | '_ \ / _ \ __\ \ /\ / / _ \| '__| |/ / / __|_  /
 *  _| |_   | | | | |  __/ |_ \ V  V / (_) | |  |   < | (__ / /
 * |_____|  |_|_| |_|\___|\__| \_/\_/ \___/|_|  |_|\_(_)___/___|
 *                                _
 *              ___ ___ ___ _____|_|_ _ _____
 *             | . |  _| -_|     | | | |     |  LICENCE
 *             |  _|_| |___|_|_|_|_|___|_|_|_|
 *             |_|
 *
 *   PROGRAMOVÁNÍ  <>  DESIGN  <>  PRÁCE/PODNIKÁNÍ  <>  HW A SW
 *
 * Tento zdrojový kód je součástí výukových seriálů na
 * IT sociální síti WWW.ITNETWORK.CZ
 *
 * Kód spadá pod licenci prémiového obsahu a vznikl díky podpoře
 * našich členů. Je určen pouze pro osobní užití a nesmí být šířen.
 * Více informací na http://www.itnetwork.cz/licence
 */

import React, { useEffect, useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { apiDelete, apiGet } from "../utils/api";
import { Link, Route } from "react-router-dom";
import PersonStatistics from "../statistics/PersonStatistics";

import PersonTable from "./PersonTable";

const ITEMS_PER_PAGE = 5;

const PersonIndex = () => {
    const [persons, setPersons] = useState([]);
    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages] = useState(1);
    const [totalElements, setTotalElements] = useState(0);

    const deletePerson = async (id) => {
        try {
            await apiDelete(`/api/persons/${id}`);
            const data = await apiGet("/api/persons", { page, size: ITEMS_PER_PAGE });
            setPersons(data.content);
            setTotalPages(data.totalPages);
            setTotalElements(data.totalElements);
        } catch (error) {
            console.log(error.message);
            alert(error.message)
        }
    };

    useEffect(() => {
        apiGet("/api/persons", { page, size: ITEMS_PER_PAGE })
            .then((data) => {
                console.log("Data z API:", data);
                setPersons(data.content);
                setTotalPages(data.totalPages);
                setTotalElements(data.totalElements);
            })
            .catch((err) => {
                console.error("Chyba při načítání osob:", err);
            });
    }, [page]);

    return (
        <div className="container">
            <div className="d-flex justify-content-center">
                <div className="btn btn-info mt-3">
                    <Link to={"/persons/statistics"} className="nav-link">
                        Statistika osob
                    </Link>
                </div>
            </div>
            <h1>Seznam osob</h1>
            <PersonTable
                totalPages={totalPages}
                setPage={setPage}
                page={page}
                deletePerson={deletePerson}
                totalElements={totalElements}
                items={persons}
            />
        </div>
    );
};
export default PersonIndex;
