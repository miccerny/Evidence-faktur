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
import { Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min.js";

const ITEMS_PER_PAGE = 5;


const PersonTable = ({items, deletePerson, page, setPage, totalPages, totalElements }) => {

    console.log("Aktuální stránka (page):", page, typeof page);

    if (!Array.isArray(items)) {
        return <div>Načítání dat…</div>;
    }

    return (
        <div className="container">
            <p>
               Počet: {totalElements}
            </p>

            <div className="row g-3">
                {items.map((person, index) => (
                    <div className="col-md-6 col-lg-12" key={person._id + 1}>
                        <div className="card shadow-sm h-100">
                            <div className="card-body position-relative">
                                <div className="d-flex justify-content-between align-items-center m-1">
                                    <h5 className="card-title">
                                        {(page * ITEMS_PER_PAGE) + index + 1} - {person.name}
                                        </h5>
                                    <div className="dropdown">
                                        <button
                                            className="btn btn-primary btn-sm dropdown-toggle"
                                            type="button"
                                            id={`dropdownMenuButton-${person._id}`}
                                            data-bs-toggle="dropdown"
                                            aria-expanded="false"
                                        >
                                            Akce
                                        </button>
                                        <ul
                                            className="dropdown-menu"
                                            aria-labelledby={`dropdownMenuButton-${person._id}`}
                                        >
                                            <li>
                                                <Link
                                                    to={"/persons/show/" + person._id}
                                                    className="dropdown-item btn btn-secondary"
                                                >
                                                    Zobrazit
                                                </Link>
                                            </li>
                                            <li>
                                                <Link
                                                    to={"/persons/edit/" + person._id}
                                                    className="dropdown-item"
                                                >
                                                    Upravit
                                                </Link>
                                            </li>
                                            <li>
                                                <button
                                                    onClick={() => 

                                                        deletePerson(person._id)
                                                    }
                                                    className="dropdown-item text-danger"
                                                >
                                                    Odstranit
                                                </button>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>


                ))}
            </div>

            <div className="d-flex justify-content-center align-items-center mt-4">
                <div>
                    <button
                        className="btn btn-outline-secondary me-2"
                        disabled={page === 0}
                        onClick={() => setPage(page - 1)}
                    >
                        Předchozí
                    </button>
                    <button
                        className="btn btn-outline-secondary"
                        disabled={page + 1 >= totalPages}
                        onClick={() => setPage(page + 1)}
                    >
                        Další
                    </button>
                    <span className="ms-3">Stránka {page + 1} z {totalPages}</span>
                </div>
            </div>

            <div className="mt-3">
                <Link to={"/persons/create"} className="btn btn-success">
                    Nová osoba
                </Link>
            </div>
            </div>
            );
};

            export default PersonTable;
