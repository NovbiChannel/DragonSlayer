package com.dragon_slayer.firebase

import com.dragon_slayer.firebase.utils.DataError
import com.dragon_slayer.firebase.utils.Result
import data.User

interface FirebaseAuthRepository {
    /**
     * user registration
     *
     * Output result:
     * @param String userId
     * @param DataError description of the error that occurred during execution
     */
    suspend fun register(userName: String, password: String): Result<User, DataError>
    /**
     * user authorization
     *
     * Output result:
     *
     * @param User user structure
     * @param DataError description of the error that occurred during execution
     */
    suspend fun authorization(userName: String, password: String): Result<User, DataError>
    /**
     * user authentication
     *
     * Output result:
     *
     * @param User user structure
     * @param DataError description of the error that occurred during execution
     */
    suspend fun authentication(userId: String): Result<User, DataError>
}