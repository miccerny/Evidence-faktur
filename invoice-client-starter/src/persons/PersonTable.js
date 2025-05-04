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

const PersonTable = ({ label, items, deletePerson }) => {


    return (
        <div className="container">
            <p>
                {label} {items.length}
            </p>

            <div className="row g-3">
                {items.map((item, index) => (
                    <div className="col-md-6 col-lg-12" key={index + 1}>
                        <div className="card shadow-sm h-100">
                            <div className="card-body position-relative">
                                <div className="d-flex justify-content-between align-items-center mb-2">
                                    <h5 className="card-title mb-0">{index + 1} - {item.name}</h5>
                                    <div className="dropdown">
                                        <button
                                            className="btn btn-primary btn-sm dropdown-toggle"
                                            type="button"
                                            id={`dropdownMenuButton-${item._id}`}
                                            data-bs-toggle="dropdown"
                                            aria-expanded="false"
                                        >
                                            Akce
                                        </button>
                                        <ul
                                            className="dropdown-menu"
                                            aria-labelledby={`dropdownMenuButton-${item._id}`}
                                        >
                                            <li>
                                                <Link
                                                    to={"/persons/show/" + item._id}
                                                    className="dropdown-item btn btn-secondary"
                                                >
                                                    Zobrazit
                                                </Link>
                                            </li>
                                            <li>
                                                <Link
                                                    to={"/persons/edit/" + item._id}
                                                    className="dropdown-item"
                                                >
                                                    Upravit
                                                </Link>
                                            </li>
                                            <li>
                                                <button
                                                    onClick={() => {

                                                        deletePerson(item._id)
                                                    }}
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


                <Link to={"/persons/create"} className="btn btn-success">
                    Nová osoba
                </Link>
            </div >
    );
};

export default PersonTable;
