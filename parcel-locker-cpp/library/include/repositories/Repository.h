//
// Created by student on 09.06.2021.
//

#ifndef INTRODUCTIONPROJECT_REPOSITORY_H
#define INTRODUCTIONPROJECT_REPOSITORY_H

#include <iostream>
#include <vector>
#include <memory>
#include <functional>
#include "typedefs.h"

template<class T> class Repository {
protected:
    std::vector<std::shared_ptr<T>> objects;

public:
    std::shared_ptr<T> get(const int &index) {
        if (objects.size() >= index && index >= 0)
            return objects.at(index);
        else
            return nullptr;
    }

    void add(std::shared_ptr<T> object) {
        if(object != nullptr)
            objects.push_back(object);
    }

    void remove(std::shared_ptr<T> object) {
        if(object != nullptr)
            for(int i = 0; i < objects.size(); i++){
                if(object == objects.at(i))
                    objects.erase(objects.begin()+i);
            }
    }

    const std::string report() const {
        std::string data = "";
        for(int i = 0; i < objects.size(); i++) {
            data += objects.at(i)->getInfo();
        }
        return data;
    }

    const int size() const {
        return objects.size();
    }

    template<class P> std::vector<std::shared_ptr<T>> findBy(const P &predicate) const {
        std::vector<std::shared_ptr<T>> found;
        for (unsigned int i = 0; i < objects.size(); i++) {
            std::shared_ptr<T> object = objects.at(i);
            if (object != nullptr && predicate(object)) {
                found.push_back(object);
            }
        }
        return found;
    }

    std::vector<std::shared_ptr<T>> findAll() {
        return this->findBy([](const std::shared_ptr<T> &object){ return true; });
    }

};

#endif //INTRODUCTIONPROJECT_REPOSITORY_H
